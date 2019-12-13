package com.example.pmu_laba_4_5v1.utils;

public class Vector2 {
    public float x;
    public float y;

    static public final float PI = 3.1415927f;
    static public final float radiansToDegrees = 180f / PI;
    static public final float degreesToRadians = PI / 180;


    public Vector2() {
    }


    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }


    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }


    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }


    public void scale(float sx, float sy) {
        this.x *= sx;
        this.y *= sy;
    }


    public Vector2 scale(float s) {
        scale(s, s);
        return this;
    }


    public static Vector2 max(Vector2 first, Vector2 second) {
        Vector2 result = new Vector2();
        result.x = Math.max(first.x, second.x);
        result.y = Math.max(first.y, second.y);

        return result;
    }


    public static Vector2 min(Vector2 first, Vector2 second) {
        Vector2 result = new Vector2();
        result.x = Math.min(first.x, second.x);
        result.y = Math.min(first.y, second.y);

        return result;
    }


    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }


    public Vector2 clamp(float min, float max) {
        final float len2 = len2();
        if (len2 == 0f) return this;
        float max2 = max * max;
        if (len2 > max2) return scale((float) Math.sqrt(max2 / len2));
        float min2 = min * min;
        if (len2 < min2) return scale((float) Math.sqrt(min2 / len2));
        return this;
    }


    public Vector2 mul(double value) {
        return new Vector2((float) value * x, (float) value * y);
    }


    public float len2() {
        return x * x + y * y;
    }


    public float cross(Vector2 v) {
        return this.x * v.y - this.y * v.x;
    }


    public Vector2 rotateRad(float radians)
    {
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        this.x = newX;
        this.y = newY;

        return this;
    }


    public Vector2 rotate(float degrees) {
        return rotateRad(degrees * degreesToRadians);
    }


    public Vector2 rotateDependOn(float degrees, Vector2 body)
    {
        Vector2 result = new Vector2();

        float radians = degrees * degreesToRadians;
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        result.x = (this.x - body.x )* cos - (this.y - body.y) * sin + body.x;
        result.y = (this.x - body.x )* sin + (this.y - body.y) * cos + body.y;

        return result;
    }


    public float magnitude()
    {
        return (float) Math.sqrt(x * x + y * y );
    }


    public void normalize()
    {
        scale(1 / magnitude());
    }


    public Vector2 lerp(Vector2 target, float alpha)
    {
        final float invAlpha = 1.0f - alpha;
        this.x = (x * invAlpha) + (target.x * alpha);
        this.y = (y * invAlpha) + (target.y * alpha);
        return this;
    }
}