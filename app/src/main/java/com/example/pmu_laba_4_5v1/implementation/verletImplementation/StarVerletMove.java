package com.example.pmu_laba_4_5v1.implementation.verletImplementation;

import com.example.pmu_laba_4_5v1.utils.Bodies;
import com.example.pmu_laba_4_5v1.utils.Body;
import com.example.pmu_laba_4_5v1.utils.Vector2;
import com.example.pmu_laba_4_5v1.utils.VerletIntegrationSystem;
import com.example.pmu_laba_4_5v1.utils.Vertex;

import java.util.List;


public class StarVerletMove extends VerletIntegrationSystem
{
	final float angle = 1.0f;

	public StarVerletMove(Bodies tBodies)
	{
		super(tBodies);
	}


	@Override
	public void setFixed() {  }


	@Override
	public void rotateObject(Body body)
	{
		Vector2 center = body.center();
		center.rotate(angle);

		List<Vertex> vertices = body.vertex;
		for (int i = 0; i < body.vertexCount; i++)
		{
			Vertex vertex = vertices.get(i);
			vertex.position.set(vertex.position.rotateDependOn(angle, center));
			vertex.oldPosition.set(vertex.position);
		}
	}
}
