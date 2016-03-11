package com.den13l.new2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class BoardAdapter extends BaseAdapter {

    private List<Cell> cells;
    private int count;

    public BoardAdapter(Context context, Model model) {
        this.cells = new ArrayList<>();
        int cellsCountInLine = model.getCellsCountInLine();
        this.count = (int) Math.pow(cellsCountInLine, 2);
        initCells(context, model);
    }

    private void initCells(Context context, Model model) {
        int cellWidth = model.getCellWidth();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < getCount(); i++) {
            RelativeLayout cellView = (RelativeLayout) inflater.inflate(R.layout.cell_view, null, false);
            cellView.setLayoutParams(new AbsListView.LayoutParams(cellWidth, cellWidth));
            cells.add(new Cell(cellView, i));
        }
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

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        return convertView == null ? cells.get(i).getCellView() : convertView;
    }

    public List<Cell> getCells() {
        return cells;
    }
}