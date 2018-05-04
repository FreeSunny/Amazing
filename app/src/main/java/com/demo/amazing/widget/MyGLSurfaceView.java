package com.demo.amazing.widget;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by hzsunyj on 17/5/2.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    public static final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private Context context;

    public final MyRender render;

    public MyGLSurfaceView(Context context) {
        super(context);
        this.context = context;
        setEGLContextClientVersion(2);
        render = new MyRender();
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    private float previousX;

    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                render.setAngle(render.getAngle() + (dx + dy) * TOUCH_SCALE_FACTOR);
                requestRender();
        }
        previousX = x;
        previousY = y;
        return true;
    }
}
