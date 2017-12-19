package com.demo.example.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.demo.example.R;
import com.demo.example.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenRecordActivity extends AppCompatActivity implements View.OnClickListener, TextureView
		.SurfaceTextureListener {

	public static final int REQUEST_CODE = 0x100;

	public static void start(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, ScreenRecordActivity.class);
		context.startActivity(intent);
	}

	private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY |
			DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;

	private boolean isRecording;

	private boolean isPlaying;

	DisplayMetrics metrics;
	MediaProjectionManager manager;
	MediaRecorder recorder;
	MediaProjection mediaProjection;
	VirtualDisplay virtualDisplay;

	Chronometer chronometer;

	MediaPlayer mediaPlayer;

	String fileName;

	Button record;

	Button play;

	TextureView textureView;

	private Surface surface;

	private int width = 720;
	private int height = 1280;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_record);
		findViews();
		initViewsListener();
		init();
	}

	private void findViews() {
		record = (Button) findViewById(R.id.record);
		play = (Button) findViewById(R.id.play);
		textureView = (TextureView) findViewById(R.id.texture);
		chronometer = (Chronometer) findViewById(R.id.update);
	}

	private void initViewsListener() {
		record.setOnClickListener(this);
		play.setOnClickListener(this);
		textureView.setSurfaceTextureListener(this);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void init() {
		metrics = this.getResources().getDisplayMetrics();
		width = 720;
		height = 1280;
		//printInfo();
		manager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void printInfo() {
		MediaCodecList list = new MediaCodecList(MediaCodecList.ALL_CODECS);
		MediaCodecInfo[] codecInfos = list.getCodecInfos();
		for (MediaCodecInfo info : codecInfos) {
			if (info.isEncoder()) {
				StringBuilder sb = new StringBuilder();
				sb.append(info.getName() + " types=");
				String[] supportedTypes = info.getSupportedTypes();
				for (String string : supportedTypes) {
					sb.append(" " + string);
				}
				LogUtil.e(sb.toString());
			}
		}
	}

	private void initMediaRecorder() {
		if (recorder == null) {
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			recorder.setVideoSize(width, height);
			recorder.setVideoEncodingBitRate(4 * width * height);
			recorder.setVideoFrameRate(30);
		} else {
			recorder.reset();
		}
		recorder.setOutputFile(getFilePath());
		prepare();
	}

	private void prepare() {
		try {
			recorder.prepare();
		} catch (IOException e) {
			e.printStackTrace();
			releaseRecorder();
		}
	}

	private String getFilePath() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHss");
		String time = dateFormat.format(date);

		String absolutePath = getExternalCacheDir().getAbsolutePath();

		StringBuilder sb = new StringBuilder();
		sb.append(absolutePath).append("/cap/").append(time).append(".mp4");
		fileName = sb.toString();
		File file = new File(fileName);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdir();
		}
		return fileName;
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.record:
				onRecord();
				break;
			case R.id.play:
				onPlay();
				break;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void onRecord() {
		if (isRecording) {
			stopRecord();
		} else {
			startRecord();
		}
		isRecording = !isRecording;
		record.setText(isRecording ? "stop" : "start");
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void startRecord() {
		if (mediaProjection != null) {// 说明已经请求过权限了
			onStartRecord();
		} else {
			Intent captureIntent = manager.createScreenCaptureIntent();
			startActivityForResult(captureIntent, REQUEST_CODE);
		}
	}

	private void stopRecord() {
		if (recorder != null) {
			recorder.stop();
			recorder.reset();
		}
	}

	private void onPlay() {
		if (isRecording) {
			Toast.makeText(this, "recording", Toast.LENGTH_LONG).show();
			return;
		}
		if (isPlaying) {
			stopPlay();
		} else {
			statPlay();
		}
		isPlaying = !isPlaying;
		play.setText(isPlaying ? "stop" : "play");
	}

	private void stopPlay() {

	}

	private void statPlay() {
		if (TextUtils.isEmpty(fileName)) {
			//
			Toast.makeText(this, "recorder fail", Toast.LENGTH_LONG).show();
			return;
		}
		initMediaPlay();
	}

	private void initMediaPlay() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		} else {
			mediaPlayer.stop();
			mediaPlayer.reset();
			mediaPlayer.release();
		}
		try {
			mediaPlayer.setDataSource(fileName);
			mediaPlayer.setSurface(surface);
			mediaPlayer.prepare();
			mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.start();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != REQUEST_CODE) {
			return;
		}
		if (manager == null) {
			return;
		}
		mediaProjection = manager.getMediaProjection(resultCode, data);
		if (mediaProjection == null) {
			return;
		}
		onStartRecord();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void onStartRecord() {
		initMediaRecorder();
		createVirtualDisplay();
		recorder.start();
		chronometer.start();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void createVirtualDisplay() {
		virtualDisplay = mediaProjection.createVirtualDisplay("ScreenRecordActivity", width, height, metrics
				.densityDpi, VIRTUAL_DISPLAY_FLAGS, recorder.getSurface(), null, null);
	}

	private void releaseRecorder() {
		if (recorder != null) {
			recorder.reset();
		}
		recorder = null;
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseDisplay();
		releaseRecorder();
		releaseProjection();
		releaseMediaPlayer();
	}

	private void releaseMediaPlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private void releaseDisplay() {
		if (virtualDisplay != null) {
			virtualDisplay.release();
			virtualDisplay = null;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void releaseProjection() {
		if (mediaProjection != null) {
			mediaProjection.stop();
			mediaProjection = null;
		}
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
		surface = new Surface(surfaceTexture);
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {

	}
}
