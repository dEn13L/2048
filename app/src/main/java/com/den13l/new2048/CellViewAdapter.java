package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class CellViewAdapter extends BaseAdapter {

    private List<CellView> cellViews;
    private int cellsCountInLine;
    private int count;

    public CellViewAdapter(Context context, Model model) {
        this.cellViews = new ArrayList<>();
        this.cellsCountInLine = model.getCellsCountInLine();
        this.count = (int) Math.pow(cellsCountInLine, 2);
        createCellViews(context, model);
    }

    private void createCellViews(Context context, Model model) {
        for (int i = 0; i < getCount(); i++) {
            CellView cellView = new CellView(context, i);
            cellView.setLayoutParams(new AbsListView.LayoutParams(model.getCellWidth(), model.getCellWidth()));
            int margin = model.getCellMargin();
            if (isFirstColumn(i)) {
                if (isFirstRaw(i)) {
                    cellView.setPadding(margin, margin, margin / 2, margin / 2);
                } else if (isLastRaw(i)) {
                    cellView.setPadding(margin, margin / 2, margin / 2, margin);
                } else {
                    cellView.setPadding(margin, margin / 2, margin / 2, margin / 2);
                }
            } else if (isLastColumn(i)) {
                if (isFirstRaw(i)) {
                    cellView.setPadding(margin / 2, margin, margin, margin / 2);
                } else if (isLastRaw(i)) {
                    cellView.setPadding(margin / 2, margin / 2, margin, margin);
                } else {
                    cellView.setPadding(margin / 2, margin / 2, margin, margin / 2);
                }
            } else {
                if (isFirstRaw(i)) {
                    cellView.setPadding(margin / 2, margin, margin / 2, margin / 2);
                } else if (isLastRaw(i)) {
                    cellView.setPadding(margin / 2, margin / 2, margin / 2, margin);
                } else {
                    cellView.setPadding(margin / 2, margin / 2, margin / 2, margin / 2);
                }
            }
            cellViews.add(cellView);
        }
    }

    private boolean isFirstColumn(int position) {
        return position % cellsCountInLine == 0;
    }

    private boolean isLastColumn(int position) {
        return (position + 1) % cellsCountInLine == 0;
    }

    private boolean isFirstRaw(int position) {
        return position / cellsCountInLine == 0;
    }

    private boolean isLastRaw(int position) {
        return position / cellsCountInLine == cellsCountInLine - 1;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public List<CellView> getCellViews() {
        return cellViews;
    }

    public View getView(int i, View convertView, ViewGroup parent) {
        return convertView == null ? cellViews.get(i) : (CellView) convertView;
    }
}