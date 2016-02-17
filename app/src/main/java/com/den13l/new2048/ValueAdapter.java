package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class ValueAdapter extends CellAdapter {

    public ValueAdapter(Context context, Model model) {
        super(context, model);
    }

    @Override
    protected void initCells() {
        for(int i = 0; i < getCount(); i++) {
            Value value = new Value(context, i, model);
            value.setBackgroundColor(Color.TRANSPARENT);
            cells.add(value);
        }
    }
}