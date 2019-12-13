package com.example.pmu_laba_4_5v1.utils;

import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glEnable;


public class OpenGLRenderer implements Renderer
{
    protected VerletIntegrationSystem system;

    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private float[] mMatrix = new float[16];

    Line[] lines;

    public OpenGLRenderer(VerletIntegrationSystem system)
    {
        this.system = system;
        lines = new Line[system.bodies.get(0).edgeCount];

        for (int i = 0; i < system.bodies.get(0).edgeCount; i++)
        {
            lines[i] = new Line();
        }
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1)
    {
        glClearColor(0f, 0f, 0f, 1f);
        glEnable(GL_DEPTH_TEST);
        createViewMatrix();
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height)
    {
        glViewport(0, 0, width, height);
        createProjectionMatrix(width, height);
        bindMatrix();
    }

    @Override
    public void onDrawFrame(GL10 arg0)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        system.Update();
        bindMatrix();

        for (int i = 0; i < system.bodies.count; i++)
        {
            Body body = system.bodies.get(i);
            for (int j = 0; j < body.edges.size(); j++)
            {
                Edge edge = body.edges.get(j);

                Line vertLine = new Line();
                vertLine.SetColor(1.0f, 0f, 0f, 1.0f);
                vertLine.SetVerts(body.vertex.get(edge.v1Index).position.x,
                        body.vertex.get(edge.v1Index).position.y,
                        0,
                        body.vertex.get(edge.v2Index).position.x,
                        body.vertex.get(edge.v2Index).position.y,
                        0);

                vertLine.draw(mMatrix);
            }

            for (int j = 0; j < body.vertex.size(); j++)
            {
                Vertex vertex = body.vertex.get(j);

                Circle circle = new Circle(vertex.position.x, vertex.position.y);
                circle.draw(mMatrix);
            }
        }
    }


    private void createProjectionMatrix(int width, int height)
    {
        float ratio = 1;
        float left = -1;
        float right = 1;
        float bottom = -1;
        float top = 1;
        float near = 1;
        float far = 200;

        if (width > height)
        {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        }
        else
        {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }


    private void createViewMatrix()
    {
        float eyeX = 0;
        float eyeY = 0;
        float eyeZ = 4f;

        float centerX = 0;
        float centerY = 0;
        float centerZ = 0;

        float upX = 0;
        float upY = 1;
        float upZ = 0;

        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }


    private void bindMatrix()
    {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mModelMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mMatrix, 0);
    }
}
