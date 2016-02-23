package com.den13l.new2048;

import android.view.animation.TranslateAnimation;

/**
 * Created by erdenierdyneev on 22.02.16.
 */
public interface SideShift {

    int getDestPosition(Value value, int shift);
    TranslateAnimation getTranslateAnimation(int shift);
}
