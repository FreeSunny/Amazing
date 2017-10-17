package com.demo.example.helper;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;


/**
 * Created by hzsunyj on 2017/9/19.
 */

public class MediaHelper implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private Activity activity;

    private MediaPlayer mediaPlayer;

    private boolean prepared;

    private boolean callStart;

    public MediaHelper(Activity activity) {
        this.activity = activity;
        //prepareMediaPlayer();
    }

    /**
     *
     */
    private void initMediaPlayer(AssetFileDescriptor afd) {
        if (afd == null) {
            return;
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.prepare();// stream
            afd.close();
            //mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
    }

    //    private void prepareMediaPlayer() {
    //        if (mediaPlayer != null) {
    //            return;
    //        }
    //        mediaPlayer = new MediaPlayer();
    //        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
    //            return;
    //        }
    //        try {
    //            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
    //            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
    //            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
    //            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");
    //
    //            Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class,
    //                    cMediaTimeProvider, iSubtitleControllerListener});
    //
    //            Object subtitleInstance = constructor.newInstance(this, null, null);
    //
    //            Field f = cSubtitleController.getDeclaredField("mHandler");
    //
    //            f.setAccessible(true);
    //            try {
    //                f.set(subtitleInstance, new Handler());
    //            } catch (IllegalAccessException e) {
    //            } finally {
    //                f.setAccessible(false);
    //            }
    //
    //            Method setsubtitleanchor = mediaPlayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController,
    //                    iSubtitleControllerAnchor);
    //
    //            setsubtitleanchor.invoke(mediaPlayer, subtitleInstance, null);
    //            //Log.e("", "subtitle is setted :p");
    //        } catch (Exception e) {
    //        }
    //
    //    }

    public void startPlay(AssetFileDescriptor afd) {
        initMediaPlayer(afd);
        callStart = true;
        onStart();
    }


    private void onStart() {
        if (prepared && callStart) {
            mediaPlayer.start();
        }
    }

    private void stopPlay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void releasePlay() {
        stopPlay();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        prepared = true;
        onStart();
    }
}
