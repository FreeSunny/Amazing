package com.demo.example.mode;

import android.opengl.GLES20;

import com.demo.example.widget.MyRender;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by hzsunyj on 17/4/27.
 */

public class Triangle {

    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3;

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition; " +
            "void main(){" + "" +
            " gl_Position = " +
            "uMVPMatrix * vPosition;" + "}";

    private final String fragmentShaderCode = "precision mediump float; " + "uniform vec4 vColor; " + "void main(){"
            + " gl_FragColor = vColor;" + "}";

    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    private final int program;

    private int positionHandle;

    private int colorHandle;

    private int mVPHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;

    private final int vertexStride = COORDS_PER_VERTEX * 4;

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    //float color[] = {0.0f, 1.0f, 0.0f, 1.0f};
    public Triangle() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);

        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();

        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = MyRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);

        int fragmentShader = MyRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);

        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);
    }

    public void draw(float[] mVPMatrix) {

        GLES20.glUseProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");


        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride,
                vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        mVPHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(mVPHandle, 1, false, mVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);


    }

}
