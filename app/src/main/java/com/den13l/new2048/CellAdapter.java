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
public class CellAdapter extends BaseAdapter {

    protected List<CellView> cellViews;
    protected int count;
    protected Context context;
    protected Model model;

    public CellAdapter(Context context, Model model) {
        this.context = context;
        this.model = model;
        this.cellViews = new ArrayList<>();
        this.count = (int) Math.pow(model.getCellsCountInLine(), 2);
        for (int i = 0; i < getCount(); i++) {
            CellView cellView = new CellView(context, i, model);
            cellView.setBackgroundColor(Color.parseColor(CellView.CELL_COLOR));
            cellViews.add(cellView);
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
        CellView cellView;
        if (convertView == null) {
            cellView = cellViews.get(position);
        } else {
            cellView = (CellView) convertView;
        }
        return cellView;
    }

    public List<CellView> getCellViews() {
        return cellViews;
    }
}