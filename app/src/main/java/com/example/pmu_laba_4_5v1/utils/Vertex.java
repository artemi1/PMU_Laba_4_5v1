package com.example.pmu_laba_4_5v1.utils;

public class Vertex
{
	public Vector2 position;
	public Vector2 oldPosition;
	public Vector2 acceleration;
	
	public Vertex(Vector2 tPosition, Vector2 tOldPosition, Vector2 tAcceleration)
	{
		position = tPosition;
		oldPosition = tOldPosition;
		acceleration = tAcceleration;
	}
}