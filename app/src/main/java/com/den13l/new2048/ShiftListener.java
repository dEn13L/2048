package com.den13l.new2048;

import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftListener implements Animation.AnimationListener {

    private List<ValueShift> valueShifts;
    private List<Value> values;
    private ShiftEndListener shiftEndListener;

    private List<Value> sourceZero;
    private List<Value> sourceShifted;
    private List<Value> destShifted;

    private ShiftDirection shiftDirection;

    public ShiftListener(List<ValueShift> valueShifts, ShiftDirection shiftDirection) {
        this.valueShifts = valueShifts;
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
        for (ValueShift valueShift : valueShifts) {
            Value destValue = valueShift.getDestValue();
            Value sourceValue = valueShift.getSourceValue();

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
        if (shiftEndListener != null) {
            shiftEndListener.onShifted(values);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setSwipeEndListener(List<Value> values, ShiftEndListener shiftEndListener) {
        this.values = values;
        this.shiftEndListener = shiftEndListener;
    }
}