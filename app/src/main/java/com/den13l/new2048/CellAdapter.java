package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class CellAdapter extends BaseAdapter {

    private Model model;
    private List<Cell> cells;

    public CellAdapter(Context context, Model model) {
        this.model = model;
        this.cells = new ArrayList<>();
        for(int i = 0; i < getCount(); i++) {
            Cell cell = new Cell(context, i, model);
            cell.setBackgroundColor(Color.parseColor(Cell.CELL_COLOR));
            cells.add(cell);
        }
    }

    @Override
    public int getCount() {
        return (int) Math.pow(model.getCellsCountInRow(), 2);
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Cell cell;
        if (convertView == null) {
            cell = cells.get(position);
        } else {
            cell = (Cell) convertView;
        }
        return cell;
    }

    public List<Cell> getCells() {
        return cells;
    }
}