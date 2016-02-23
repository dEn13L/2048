package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftAnimation {

    private List<Shift> shifts;
    private List<Value> values;
    private SwipeEndListener swipeEndListener;
    private ShiftDirection shiftDirection;

    public ShiftAnimation(List<Value> values, ShiftDirection shiftDirection) {
        this.shifts = new ArrayList<>();
        this.values = values;
        this.shiftDirection = shiftDirection;
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public void setSwipeEndListener(SwipeEndListener swipeEndListener) {
        this.swipeEndListener = swipeEndListener;
    }

    public void start() {
        TranslateAnimation tr = null;
        for (Shift shift : shifts) {
            Value sourceValue = shift.getSourceValue();
            tr = shift.getTr();
            sourceValue.startAnimation(tr);
        }
        if (tr != null && swipeEndListener != null) {
            ShiftListener shiftListener = new ShiftListener(shifts, shiftDirection);
            shiftListener.setSwipeEndListener(values, swipeEndListener);
            tr.setAnimationListener(shiftListener);
        }
    }
}