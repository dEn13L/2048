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
    private int cellsCountInLine;
    private int count;

    public BoardAdapter(Context context, Model model) {
        this.cells = new ArrayList<>();
        this.cellsCountInLine = model.getCellsCountInLine();
        this.count = (int) Math.pow(cellsCountInLine, 2);
        initCells(context, model);
    }

    private void initCells(Context context, Model model) {
        int cellWidth = model.getCellWidth();
        int padding = model.getCellPadding();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < getCount(); i++) {
            RelativeLayout cellView = (RelativeLayout) inflater.inflate(R.layout.cell_view, null, false);
            cellView.setLayoutParams(new AbsListView.LayoutParams(cellWidth, cellWidth));
//            if (isFirstColumn(i)) {
//                if (isFirstRaw(i)) {
//                    cellView.setPadding(0, 0, padding / 2, padding / 2);
//                } else if (isLastRaw(i)) {
//                    cellView.setPadding(0, padding / 2, padding / 2, 0);
//                } else {
//                    cellView.setPadding(0, padding / 2, padding / 2, padding / 2);
//                }
//            } else if (isLastColumn(i)) {
//                if (isFirstRaw(i)) {
//                    cellView.setPadding(padding / 2, 0, 0, padding / 2);
//                } else if (isLastRaw(i)) {
//                    cellView.setPadding(padding / 2, padding / 2, 0, 0);
//                } else {
//                    cellView.setPadding(padding / 2, padding / 2, 0, padding / 2);
//                }
//            } else {
//                if (isFirstRaw(i)) {
//                    cellView.setPadding(padding / 2, 0, padding / 2, padding / 2);
//                } else if (isLastRaw(i)) {
//                    cellView.setPadding(padding / 2, padding / 2, padding / 2, 0);
//                } else {
//                    cellView.setPadding(padding / 2, padding / 2, padding / 2, padding / 2);
//                }
//            }
            cells.add(new Cell(cellView, i));
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

    public List<Cell> getCells() {
        return cells;
    }

    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            RelativeLayout cellView = cells.get(i).getCellView();
            return cellView;
        } else {
            return convertView;
        }
    }
}