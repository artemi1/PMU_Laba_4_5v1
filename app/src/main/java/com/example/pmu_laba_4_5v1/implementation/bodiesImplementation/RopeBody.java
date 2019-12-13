package com.example.pmu_laba_4_5v1.implementation.bodiesImplementation;

import com.example.pmu_laba_4_5v1.utils.Body;
import com.example.pmu_laba_4_5v1.utils.Edge;
import com.example.pmu_laba_4_5v1.utils.Vector2;
import com.example.pmu_laba_4_5v1.utils.Vertex;

public class RopeBody extends Body
{
    @Override
    public void initialize()
    {
        vertexCount = 15;
	    edgeCount = vertexCount - 1;
        float restlength = .20f;

        fillLists();

        for (int i = 0; i < vertexCount; i++)
	    {
            Vector2 currentPosition = new Vector2(0.5f, 0 + i * restlength);
            Vector2 oldPosition = new Vector2(currentPosition);
            Vector2 acceleration = new Vector2(0, 0);
	        vertex.set(i , new Vertex(currentPosition, oldPosition, acceleration));
        }

		for (int p = 0; p < edgeCount; p++)
		{
            edges.set(p, new Edge(this, p, p + 1));
        }
    }
}
