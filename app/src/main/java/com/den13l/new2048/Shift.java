package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class Shift {

    private Value sourceValue;
    private Value destValue;
    private TranslateAnimation tr;

    Shift(Value sourceValue, Value destValue, TranslateAnimation tr) {
        this.sourceValue = sourceValue;
        this.destValue = destValue;
        this.tr = tr;
    }

    public Value getSourceValue() {
        return sourceValue;
    }

    public Value getDestValue() {
        return destValue;
    }

    public TranslateAnimation getTr() {
        return tr;
    }
}
