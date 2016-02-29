package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class Shift {

    private CellView sourceCell;
    private CellView destCell;
    private TranslateAnimation translateAnimation;
    private int destNumber;

    Shift(CellView sourceCell, CellView destCell, int cellsCountInLine, int cellWidth) {
        this.sourceCell = sourceCell;
        this.destCell = destCell;
        this.destNumber = getSourceNumber();
        int shiftCount = destCell.getPosition() - sourceCell.getPosition();
        if (Math.abs(shiftCount) < cellsCountInLine) {
            // right or left shift
            translateAnimation = new TranslateAnimation(0, cellWidth * shiftCount, 0, 0);
            translateAnimation.setDuration(500);
        } else {
            // top or bottom shift
            translateAnimation = new TranslateAnimation(0, 0, 0, cellWidth * (shiftCount / cellsCountInLine));
            translateAnimation.setDuration(500);
        }
    }

    public void start(ShiftListener shiftListener) {
        shiftListener.setShift(this);
        translateAnimation.setAnimationListener(shiftListener);
        sourceCell.startAnimation(translateAnimation);
    }

    @Override
    public String toString() {
        return "Shift. Source: " + sourceCell.toString() + ", Dest: " + destCell.toString() + ", destNumber:" +
                destNumber;
    }

    public TranslateAnimation getTranslateAnimation() {
        return translateAnimation;
    }

    public CellView getSourceCell() {
        return sourceCell;
    }

    public CellView getDestCell() {
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