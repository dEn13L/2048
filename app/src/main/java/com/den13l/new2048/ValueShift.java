package com.den13l.new2048;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ValueShift {

    private Value sourceValue;
    private Value destValue;
    private int destNumber;
    private TranslateAnimation tr;

    ValueShift(Value sourceValue, final Value destValue, int cellsCountInLine, int cellWidth) {
        this.sourceValue = sourceValue;
        this.destValue = destValue;
        this.destNumber = sourceValue.getNumber();

        int shift = destValue.getPosition() - sourceValue.getPosition();
        if (shift > 0) {
            if (shift >= cellsCountInLine) {
                // bottom shift
                this.tr = new TranslateAnimation(0, 0, 0, cellWidth * shift);
            } else {
                // right shift
                this.tr = new TranslateAnimation(0, cellWidth * shift, 0, 0);
            }
        } else {
            if (-shift >= cellsCountInLine) {
                // top shift
                this.tr = new TranslateAnimation(0, 0, 0, -cellWidth * shift);
            } else {
                // left shift
                this.tr = new TranslateAnimation(0, -cellWidth * shift, 0, 0);
            }
        }
        this.tr.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                destValue.setNumber(destNumber);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    ValueShift(Value sourceValue, Value destValue, TranslateAnimation tr) {
        this.sourceValue = sourceValue;
        this.destValue = destValue;
    }

    public void start() {
        sourceValue.startAnimation(tr);
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

    public int getSourceNumber() {
        return sourceValue.getNumber();
    }

    public int getSourcePosition() {
        return sourceValue.getPosition();
    }

    public int getDestPosition() {
        return destValue.getPosition();
    }

    public int getDestNumber() {
        return destNumber;
    }
}
