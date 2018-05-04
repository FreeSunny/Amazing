package com.demo.amazing.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.demo.amazing.R;
import com.demo.amazing.util.BitmapUtil;
import com.demo.amazing.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback,
        Camera.PictureCallback {

    public static void start(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    private SurfaceView surfaceView;

    private Camera camera;

    private SurfaceHolder holder;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        findViews();
        initSurface();
    }

    private void findViews() {
        surfaceView = (SurfaceView) findViewById(R.id.camera_surface);
        imageView = (ImageView) findViewById(R.id.take_image);
    }

    private void initSurface() {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
    }

    private void initCamera() {
        try {
            int numberOfCameras = Camera.getNumberOfCameras();
            if (numberOfCameras > 1) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                camera = Camera.open();
            }
            camera.setPreviewDisplay(holder);
            setParams();
            camera.setPreviewCallback(this);
            camera.startPreview();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    //camera.takePicture(null, null, CameraActivity.this);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setParams() {
        //LogUtil.e("preview set size=" + width + " : " + height);
        Camera.Parameters parameters = camera.getParameters();
        //        parameters.setPreviewSize(width, height);
        //        parameters.setPictureSize(width, height);
        parameters.setPreviewFormat(ImageFormat.NV21);
        camera.setDisplayOrientation(90);
        parameters.setRotation(90);

        List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
        for (Integer integer : supportedPreviewFormats) {
            //LogUtil.e("preview format=" + integer);
        }

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : supportedPreviewSizes) {
            //LogUtil.e("preview size=" + size.width + " : " + size.height);
        }
        camera.setParameters(parameters);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        release();
    }

    private void release() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            Camera.Size previewSize = camera.getParameters().getPreviewSize();
            LogUtil.e("preview size=" + previewSize.width + " : " + previewSize.height);

            YuvImage image = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 80, stream);

            Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

            stream.close();
            bitmap = BitmapUtil.rotateBitmap(this, bitmap, 270);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File file = new File(getImagePath());
        if (file == null) {
            return;
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            LogUtil.e("image orientation:" + exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
            LogUtil.e("image width:" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
            LogUtil.e("image height:" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    private String getImagePath() {
        //String path = getFilesDir()
        LogUtil.e("files dir:" + getFilesDir());
        return getFilesDir() + File.separator + "image.jpeg";
    }
}
