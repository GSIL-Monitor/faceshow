package com.faceshow.common.utils.mapbean;


import java.io.Serializable;

public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;

    private double y;

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }
}
