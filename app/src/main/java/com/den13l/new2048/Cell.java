package com.den13l.new2048;

import android.graphics.Color;

/**
 * Created by erdenierdyneev on 28.02.16.
 */
public class Cell {

    private int number;
    private int position;

    public Cell(int position) {
        this(position, 0);
    }

    public Cell(int position, int number) {
        this.position = position;
        this.number = number;
    }

    @Override
    public String toString() {
        return position + ":" + number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean hasNumber() {
        return getNumber() != 0;
    }

    public int getNumber() {
        return number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
