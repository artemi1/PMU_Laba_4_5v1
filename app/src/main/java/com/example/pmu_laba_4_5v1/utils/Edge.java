package com.example.pmu_laba_4_5v1.utils;


public class Edge
{
	public int v1Index = -1;
	public int v2Index = -1;
	
	public Body body;
	public float length = 2;
	public double elastic = 0.5;
	
	public Edge(Body tBody, int v1, int v2)
	{
		body = tBody;
		v1Index = v1;
		v2Index = v2;
		length = (float) Math.sqrt(
				Math.pow(tBody.vertex.get(v1).position.x - tBody.vertex.get(v2).position.x, 2) +
				Math.pow(tBody.vertex.get(v1).position.y - tBody.vertex.get(v2).position.y, 2));

	}
}