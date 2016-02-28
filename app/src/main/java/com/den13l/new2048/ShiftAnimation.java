package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftAnimation {

    private List<ValueShift> valueShifts;
    private List<Value> values;
    private ShiftEndListener shiftEndListener;
    private ShiftDirection shiftDirection;

    public ShiftAnimation(List<Value> values, ShiftDirection shiftDirection) {
        this.valueShifts = new ArrayList<>();
        this.values = values;
        this.shiftDirection = shiftDirection;
    }

    public void addShift(ValueShift valueShift) {
        valueShifts.add(valueShift);
    }

    public void setShiftEndListener(ShiftEndListener shiftEndListener) {
        this.shiftEndListener = shiftEndListener;
    }

    public void start() {
        TranslateAnimation tr = null;
        for (ValueShift valueShift : valueShifts) {
            Value sourceValue = valueShift.getSourceValue();
            tr = valueShift.getTr();
            sourceValue.startAnimation(tr);
        }
        if (tr != null && shiftEndListener != null) {
            ShiftListener shiftListener = new ShiftListener(valueShifts, shiftDirection);
            shiftListener.setSwipeEndListener(values, shiftEndListener);
            tr.setAnimationListener(shiftListener);
        }
    }
}