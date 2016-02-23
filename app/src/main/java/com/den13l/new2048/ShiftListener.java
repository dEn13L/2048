package com.den13l.new2048;

import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftListener implements Animation.AnimationListener {

    private List<Shift> shifts;
    private List<Value> values;
    private SwipeEndListener swipeEndListener;

    private List<Value> sourceZero;
    private List<Value> sourceShifted;
    private List<Value> destShifted;

    private ShiftDirection shiftDirection;

    public ShiftListener(List<Shift> shifts, ShiftDirection shiftDirection) {
        this.shifts = shifts;
        this.sourceZero = new ArrayList<>();
        this.sourceShifted = new ArrayList<>();
        this.destShifted = new ArrayList<>();
        this.shiftDirection = shiftDirection;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        for (Shift shift : shifts) {
            Value destValue = shift.getDestValue();
            Value sourceValue = shift.getSourceValue();

            destShifted.add(destValue);
            sourceShifted.add(sourceValue);

            destValue.setNumber(sourceValue.getNumber());
        }
        for (Value sourceValue : sourceShifted) {
            boolean found = false;
            for (Value destValue : destShifted) {
                if (sourceValue.equals(destValue)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                sourceZero.add(sourceValue);
            }
        }
        for (Value value : sourceZero) {
            value.setNumber(0);
        }
        if (swipeEndListener != null) {
            swipeEndListener.onSwiped(values);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setSwipeEndListener(List<Value> values, SwipeEndListener swipeEndListener) {
        this.values = values;
        this.swipeEndListener = swipeEndListener;
    }
}