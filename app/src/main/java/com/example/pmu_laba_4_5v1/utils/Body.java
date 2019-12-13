package com.example.pmu_laba_4_5v1.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class Body
{
	public List<Vertex> vertex;
	public int vertexCount = 0;
	public int edgeCount = 0;
	public List<Edge> edges = new ArrayList<>();
	public boolean canSetFixedPoint = true;
	protected Context context;


	public Body()
	{
		initialize();
	}

	
	public Vector2 center()
	{
		if (vertexCount == 0)
		{
			return new Vector2(0,0);
		}
		
		double sumX = 0;
		double sumY = 0;
		
		for(int i = 0 ; i < vertexCount ; i++)
		{
			sumX += vertex.get(i).position.x;
			sumY += vertex.get(i).position.y;
		}
		
		return new Vector2((float)sumX / vertexCount, (float) sumY / vertexCount);
	}


	public float[] projectToAxis(Vector2 axis)
	{
		float dotProduct = axis.dot(vertex.get(0).position);
		
		float min = dotProduct;
		float max = dotProduct;
		for(int i = 1 ; i < vertexCount ; i++)
		{
			dotProduct = axis.dot(vertex.get(i).position);
			min = Math.min(dotProduct, min);
			max = Math.max(dotProduct, max);
		}
		
		return new float[]{min, max};
	}


	protected void fillLists()
	{
		vertex = new ArrayList<>(vertexCount);
		for(int i = 0 ; i < vertexCount ; i++)
		{
			Vector2 zeroVector = new Vector2(0,0);
			vertex.add(new Vertex(zeroVector, zeroVector, zeroVector));
		}

		edges = new ArrayList<>(edgeCount);
		for(int i = 0 ; i < edgeCount; i++)
		{
			edges.add(new Edge(this, 0,0));
		}
	}


	public abstract void initialize();
}
