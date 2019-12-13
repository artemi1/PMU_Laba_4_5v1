package com.example.pmu_laba_4_5v1.implementation.bodiesImplementation;

import com.example.pmu_laba_4_5v1.utils.Body;
import com.example.pmu_laba_4_5v1.utils.Edge;
import com.example.pmu_laba_4_5v1.utils.Vector2;
import com.example.pmu_laba_4_5v1.utils.Vertex;

import android.util.Log;

public class StarBody extends Body
{
    @Override
    public void initialize()
    {
        final float pi = 3.1415926535f;                 // тупо число Pi

        int spikesCount = 6;                            // количество лучей звезды
        float r_max = 3.0f;			                    // внешний радиус (лучи)

        float r_min = r_max / 2.0f;                     // внутренний радиус (впадины)
        float spike_angle = 2.0f * pi / spikesCount;	// угол между лучами

        vertexCount = spikesCount * 2;
        edgeCount = spikesCount * 2;


        fillLists();

        // вершины по кругу
        for (int i = 0; i < vertexCount; i += 2) {
            float x, y;

            // координаты вершины луча
            x = r_max * (float) Math.sin(spike_angle * (i / 2));
            y = r_max * (float) Math.cos(spike_angle * (i / 2));
            vertex.set(i, new Vertex(new Vector2(x, y), new Vector2(x, y), new Vector2(0, 0)));

            // координаты впадины между лучами
            x = r_min * (float) Math.sin(spike_angle * (i / 2) + spike_angle / 2);
            y = r_min * (float) Math.cos(spike_angle * (i / 2) + spike_angle / 2);
            vertex.set(i+1, new Vertex(new Vector2(x, y), new Vector2(x, y), new Vector2(0, 0)));
        }

        // центральная вершина
        //vertex.set(vertexCount-1, new Vertex(new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0)));

        // ребра по кругу
        for (int i = 0; i < edgeCount; i++) {
            int j;
            if (i != edgeCount-1){ j=i+1; } else { j=0; }
            edges.set(i, new Edge(this, i, j));
        }

        // ребра от центра к вершинам
        //for (int i = edgeCount-spikesCount; i < edgeCount; i++) {
        //    edges.set(i, new Edge(this, i, vertexCount-1));
        //}
    }
}
