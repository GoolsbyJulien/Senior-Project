package com.jra.api.util;

public class Vector {

    public int x, y;


    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void zero() {

        x = y = 0;
    }

    public Vector() {
        zero();
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
