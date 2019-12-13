package com.example.pmu_laba_4_5v1.implementation.verletImplementation;

import com.example.pmu_laba_4_5v1.utils.Bodies;
import com.example.pmu_laba_4_5v1.utils.Body;
import com.example.pmu_laba_4_5v1.utils.VerletIntegrationSystem;
import com.example.pmu_laba_4_5v1.utils.Vertex;


public class FixedCubeVerletMove extends VerletIntegrationSystem
{
	public FixedCubeVerletMove(Bodies tBodies)
	{
		super(tBodies);
	}


	@Override
	public void setFixed()
	{
		Vertex vertex = bodies.get(0).vertex.get(0);
		vertex.position.set(1f, 1f);
	}

	@Override
	public void rotateObject(Body vertex) { }
}
