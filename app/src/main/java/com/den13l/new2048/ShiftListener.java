package com.den13l.new2048;

import android.util.Log;
import android.view.animation.Animation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftListener implements Animation.AnimationListener {

    private Shift shift;
    private ShiftAnimation shiftAnimation;
    private boolean cleanSourceCell;

    public ShiftListener(ShiftAnimation shiftAnimation, boolean cleanSourceCell) {
        this.shiftAnimation = shiftAnimation;
        this.cleanSourceCell = cleanSourceCell;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.d(GameActivity.TAG, "onAnimationEnd. " + shift.toString());
        int destNumber = shift.getDestNumber();
        CellView sourceCell = shift.getSourceCell();
        CellView destCell = shift.getDestCell();
        if (!destCell.equals(sourceCell)) {
            destCell.setNumber(destNumber);
        }
        if (cleanSourceCell) {
            sourceCell.setNumber(0);
        }
        shiftAnimation.onAnimationEnd(animation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}