package com.den13l.new2048;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class Shift {

    private Cell sourceCell;
    private Cell destCell;
    private int destNumber;

    Shift(Cell sourceCell, Cell destCell) {
        this.sourceCell = sourceCell;
        this.destCell = destCell;
        this.destNumber = getSourceNumber();
    }

    @Override
    public String toString() {
        return "Source: " + sourceCell.toString() + ", Dest: " + destCell.toString() + ", destNumber:" + destNumber;
    }

    public Cell getSourceCell() {
        return sourceCell;
    }

    public Cell getDestCell() {
        return destCell;
    }

    public int getSourcePosition() {
        return sourceCell.getPosition();
    }

    public int getDestPosition() {
        return destCell.getPosition();
    }

    public int getSourceNumber() {
        return sourceCell.getNumber();
    }

    public void setDestNumber(int destNumber) { this.destNumber = destNumber; }

    public int getDestNumber() {
        return destNumber;
    }
}