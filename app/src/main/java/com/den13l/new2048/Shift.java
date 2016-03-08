package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class Shift {

    private Cell sourceCell;
    private Cell destCell;
    private TranslateAnimation translateAnimation;
    private int destNumber;

    Shift(Cell sourceCell, Cell destCell, int cellsCountInLine, int cellWidth) {
        this.sourceCell = sourceCell;
        this.destCell = destCell;
        this.destNumber = getSourceNumber();
        int shiftCount = destCell.getPosition() - sourceCell.getPosition();
        if (shiftCount != 0) {
            if (Math.abs(shiftCount) < cellsCountInLine) {
                // right or left shift
                translateAnimation = new TranslateAnimation(0, cellWidth * shiftCount, 0, 0);
                translateAnimation.setDuration(200);
            } else {
                // top or bottom shift
                translateAnimation = new TranslateAnimation(0, 0, 0, cellWidth * (shiftCount / cellsCountInLine));
                translateAnimation.setDuration(200);
            }
        }
    }

    @Override
    public String toString() {
        return "Shift. Source: " + sourceCell.toString() + ", Dest: " + destCell.toString() + ", destNumber:" +
                destNumber;
    }

    public TranslateAnimation getTranslateAnimation() {
        return translateAnimation;
    }

    public Cell getSourceCell() {
        return sourceCell;
    }

    public Cell getDestCell() {
        return destCell;
    }

    public int getSourceNumber() {
        return sourceCell.getNumber();
    }

    public int getDestNumber() {
        return destNumber;
    }

    public void setDestNumber(int destNumber) {
        this.destNumber = destNumber;
    }
}