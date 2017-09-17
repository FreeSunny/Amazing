package com.demo.example.widget;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.demo.example.mode.Square;
import com.demo.example.mode.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hzsunyj on 17/5/2.
 */
public class MyRender implements GLSurfaceView.Renderer {

    private Triangle triangle;

    private Square square;

    private final float[] projectionMatrix = new float[16];

    private final float[] viewMatrix = new float[16];

    private final float[] mVPMatrix = new float[16];

    private final float[] RotationMatrix = new float[16];

    private volatile float angle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        triangle = new Triangle();
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        float[] scratch = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Log.e("angle", "angle=" + angle);
        Matrix.setRotateM(RotationMatrix, 0, angle, 0, 0, -1.0f);

        Matrix.multiplyMM(scratch, 0, mVPMatrix, 0, RotationMatrix, 0);

        triangle.draw(scratch);
    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
