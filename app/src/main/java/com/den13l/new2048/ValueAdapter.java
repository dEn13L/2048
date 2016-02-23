package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class ValueAdapter extends BaseAdapter {

    protected List<Value> values;
    protected int count;
    protected Context context;
    protected Model model;

    public ValueAdapter(Context context, Model model) {
        this.context = context;
        this.model = model;
        this.values = new ArrayList<>();
        this.count = (int) Math.pow(model.getCellsCountInRow(), 2);
        for (int i = 0; i < getCount(); i++) {
            Value value = new Value(context, i, model);
            value.setBackgroundColor(Color.parseColor(Cell.CELL_COLOR));
            values.add(value);
        }
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(GameActivity.TAG, "getView");
        Value value;
        if (convertView == null) {
            value = values.get(position);
        } else {
            value = (Value) convertView;
        }
        return value;
    }

    public List<Value> getValues() {
        return values;
    }
}