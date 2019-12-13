package com.example.pmu_laba_4_5v1.implementation.bodiesImplementation;

import com.example.pmu_laba_4_5v1.utils.Body;
import com.example.pmu_laba_4_5v1.utils.Edge;
import com.example.pmu_laba_4_5v1.utils.Vector2;
import com.example.pmu_laba_4_5v1.utils.Vertex;

public class FixedCubeBody extends Body
{
    @Override
    public void initialize()
    {
        vertexCount = 4;
        edgeCount = 6;
		float restlength = 1f;
		fillLists();

        Vector2 m_currentPosition[] = new Vector2[vertexCount];
        Vector2 m_oldPosition[] = new Vector2[vertexCount];
        Vector2 m_acceleration[] = new Vector2[vertexCount];

		float dist = restlength / 2;

		m_currentPosition[0] = new Vector2(0.240f - dist, 0.400f - dist);
		m_currentPosition[1] = new Vector2(0.240f + dist, 0.400f - dist);
		m_currentPosition[2] = new Vector2(0.240f + dist, 0.400f + dist);
		m_currentPosition[3] = new Vector2(0.240f - dist, 0.400f + dist);

		for (int i = 0; i < vertexCount; i++)
		{
			m_oldPosition[i] = new Vector2(m_currentPosition[i]);
			m_acceleration[i] = new Vector2(0, 0);
			vertex.set(i, new Vertex(m_currentPosition[i], m_oldPosition[i], m_acceleration[i]));
		}

        edges.set(0, new Edge(this, 0, 1));
        edges.set(1, new Edge(this, 1, 2));
        edges.set(2, new Edge(this, 2, 3));
        edges.set(3, new Edge(this, 3, 0));
        edges.set(4, new Edge(this, 0, 2));
        edges.set(5, new Edge(this, 1, 3));
    }
}
