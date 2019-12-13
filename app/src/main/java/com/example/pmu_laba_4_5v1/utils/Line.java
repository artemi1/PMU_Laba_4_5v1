package com.example.pmu_laba_4_5v1.utils;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Line
{
    private FloatBuffer VertexBuffer;

    private final String VertexShaderCode =
            "uniform mat4 uMVPMatrix;" +

                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String FragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    protected int GlProgram;
    protected int PositionHandle;
    protected int ColorHandle;
    protected int MVPMatrixHandle;

    static final int COORDS_PER_VERTEX = 3;
    static float LineCoords[] = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f
    };

    private final int VertexCount = LineCoords.length / COORDS_PER_VERTEX;
    private final int VertexStride = COORDS_PER_VERTEX * 4;

    float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };

    public Line()
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                LineCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        VertexBuffer = bb.asFloatBuffer();
        VertexBuffer.put(LineCoords);
        VertexBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShaderCode);

        GlProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(GlProgram, vertexShader);
        GLES20.glAttachShader(GlProgram, fragmentShader);
        GLES20.glLinkProgram(GlProgram);
    }


    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void SetVerts(float v0, float v1, float v2, float v3, float v4, float v5) {
        LineCoords[0] = v0;
        LineCoords[1] = v1;
        LineCoords[2] = v2;
        LineCoords[3] = v3;
        LineCoords[4] = v4;
        LineCoords[5] = v5;

        VertexBuffer.put(LineCoords);
        VertexBuffer.position(0);
    }

    public void SetColor(float red, float green, float blue, float alpha) {
        color[0] = red;
        color[1] = green;
        color[2] = blue;
        color[3] = alpha;
    }

    public void draw(float[] mvpMatrix)
    {
        GLES20.glUseProgram(GlProgram);

        PositionHandle = GLES20.glGetAttribLocation(GlProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(PositionHandle);

        GLES20.glVertexAttribPointer(PositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                VertexStride, VertexBuffer);

        ColorHandle = GLES20.glGetUniformLocation(GlProgram, "vColor");

        GLES20.glUniform4fv(ColorHandle, 1, color, 0);

        MVPMatrixHandle = GLES20.glGetUniformLocation(GlProgram, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, VertexCount);
        GLES20.glDisableVertexAttribArray(PositionHandle);
    }
}
