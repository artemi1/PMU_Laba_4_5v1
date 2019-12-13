package com.example.pmu_laba_4_5v1.utils;

public abstract class VerletIntegrationSystem
{
    public Bodies bodies;
    public Vector2 vGravity;
    public Vector2 vFixedPoint;
    public int fTimeStep = 1;

    float collisionDepth = 0;
    Vector2 collisionNormal = null;
    Edge collisionEdge = null;
    Body collisionBody = null;
    Vertex collisionVertex = null;

    private int collisionVertexIndex;
    private int collisionVertexBodyIndex;
    private int collisionBodyIndex;

    public VerletIntegrationSystem(Bodies tBodies)
    {
        bodies = tBodies;
        vGravity = new Vector2(0, -0.02f);
        vFixedPoint = new Vector2(0,0);
    }

    public void Update()
    {
        updateForces();
        updateVerlet();
        //iterateCollisions();
        satisfyConstraints();
    }


    public void updateForces()
    {
        for(int i = 0 ; i < bodies.count ; i++)
        {
            Body body = bodies.get(i);
            for(int j = 0 ; j < body.vertexCount ; j++)
            {
                body.vertex.get(j).acceleration.set(vGravity);
            }
            bodies.set(i, body);
        }
    }


    public void updateVerlet()
    {
        for (int i = 0 ; i < bodies.count ; i++)
        {
            Body body = bodies.get(i);
            for (int j = 0 ; j < body.vertexCount; j++)
            {
                Vertex vertex = body.vertex.get(j);

                Vector2 x = vertex.position;
                Vector2 temp = new Vector2(x);
                Vector2 temp2 = new Vector2(x);

                Vector2 oldx = vertex.oldPosition;
                Vector2 a = vertex.acceleration;

                Vector2 tmp1 = x.sub(oldx);
                Vector2 tmp2 = a.scale(fTimeStep  * fTimeStep);
                Vector2 tmp3 = tmp2.add(tmp1);
                Vector2 tmp4 = tmp3.add(temp2);

                vertex.oldPosition = temp;
                vertex.position = tmp4;

                body.vertex.set(j, vertex);
            }

            rotateObject(body);
            bodies.set(i, body);
        }
    }


    public void satisfyConstraints()
    {
        for (int i = 0 ; i < bodies.count ; i++)
        {
            Body body = bodies.get(i);
            for (int k = 0; k < 10; k++)
            {
                for(int j = 0 ; j < body.vertexCount ; j++)
                {
                    Vertex vertex = body.vertex.get(j);
                    vertex.position = Vector2.max(Vector2.min(vertex.position, new Vector2(6.5f, 6.5f)), new Vector2(-6.5f,-6.5f));
                }

                for(int j = 0 ; j < body.edgeCount; j++)
                {
                    Edge edge = body.edges.get(j);
                    Vertex v1 = body.vertex.get(edge.v1Index);
                    Vertex v2 = body.vertex.get(edge.v2Index);

                    float deltaX = v1.position.x - v2.position.x;
                    float deltaY = v1.position.y - v2.position.y;

                    float deltalength = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    float diff = (deltalength - edge.length) / deltalength;

                    v2.position.x += deltaX * edge.elastic * diff;
                    v1.position.x -= deltaX * edge.elastic * diff;
                    v2.position.y += deltaY * edge.elastic * diff;
                    v1.position.y -= deltaY * edge.elastic * diff;
                }

                if (body.canSetFixedPoint)
                {
                    setFixed();
                }
            }
        }
    }

/*

    public boolean detectCollision(int bIndex1, int bIndex2)
    {
        Body firstCollistionObject = bodies.get(bIndex1);
        Body secondCollistionObject = bodies.get(bIndex2);
        Body temporaryObject = null;

        float minDistance = 10000;
        for (int i = 0 ; i < (firstCollistionObject.edgeCount + secondCollistionObject.edgeCount); i++)
        {
            Edge e;
            if (i < firstCollistionObject.edgeCount)
            {
                e = firstCollistionObject.edges.get(i);
                temporaryObject = firstCollistionObject;
                collisionBodyIndex = bIndex1;
            }
            else
            {
                e = secondCollistionObject.edges.get(i - firstCollistionObject.edgeCount);
                temporaryObject = secondCollistionObject;
                collisionBodyIndex = bIndex2;
            }

            Vector2 axis = new Vector2(
                    temporaryObject.vertex.get(e.v1Index).position.y - temporaryObject.vertex.get(e.v2Index).position.y,
                    temporaryObject.vertex.get(e.v1Index).position.x - temporaryObject.vertex.get(e.v2Index).position.x
            );

            axis.normalize();

            float minA, minB, maxA, maxB;

            float p1[] = firstCollistionObject.projectToAxis(axis);
            minA = p1[0];
            maxA = p1[1];

            float p2[] = secondCollistionObject.projectToAxis(axis);
            minB = p2[0];
            maxB = p2[1];

            float distance = intervalDistance(minA, maxA, minB, maxB);

            if (distance > 0)
            {
                return false;
            }
            else if (Math.abs(distance) < minDistance)
            {
                minDistance = Math.abs(distance);
                collisionNormal = axis;
                collisionEdge = e;
                collisionBody = temporaryObject;
            }
        }

        collisionDepth = minDistance;

        if (collisionBodyIndex != bIndex2)
        {
            Body temp = secondCollistionObject;
            secondCollistionObject = firstCollistionObject;
            firstCollistionObject = temp;
        }

        Vector2 center1 = firstCollistionObject.center();
        Vector2 center2 = secondCollistionObject.center();
        int sign = (int)Math.signum(collisionNormal.dot(center1.sub(center2)));

        if (sign != 1)
        {
            collisionNormal.scale(-1);
        }

        float smallestD = 10000;
        for(int i = 0 ; i < firstCollistionObject.vertexCount ; i++)
        {
            float distance = collisionNormal.dot(firstCollistionObject.vertex.get(i).position.sub(center2));
            if (distance < smallestD)
            {
                smallestD = distance;
                collisionVertex = firstCollistionObject.vertex.get(i);
                collisionVertexBodyIndex = bIndex1;
                collisionVertexIndex = i;
            }
        }

        return true;
    }


    public float intervalDistance(float minA, float maxA, float minB, float maxB)
    {
        if (minA < minB)
        {
            return minB - maxA;
        }
        return minA - maxB;
    }


    public void processCollision() { }


    public void iterateCollisions()
    {
        if (bodies.count > 1)
        {
            for (int i = 0 ; i < bodies.count ; i++)
            {
                for (int j = 0 ; j < bodies.count ; j++)
                {
                    if (j != i)
                    {
                        if (detectCollision(i, j))
                        {
                            processCollision();
                        }
                    }
                }
            }
        }
    }

 */

    public void updateGravity(float p_x, float p_y) { vGravity.set(p_x, p_y); }


    public void setFixedPoint(float p_x, float p_y)
    {
        vFixedPoint.set(p_x, p_y);
    }


    public void setGravity(float x, float y)
    {
        vFixedPoint.set(x, y);
    }


    public abstract void setFixed();


    public abstract void rotateObject(Body body);
}