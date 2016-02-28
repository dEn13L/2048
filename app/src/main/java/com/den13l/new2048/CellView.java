package com.den13l.new2048;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class CellView extends RelativeLayout {

    public static final String CELL_COLOR = "#BBBBBB";

    protected RelativeLayout paddingCell;
    private Model model;
    private int cellsCountInRow;
    protected int position;

    public CellView(CellView cellView) {
        this(cellView.getContext(), cellView.getPosition(), cellView.getModel());
    }

    public CellView(Context context, int position, Model model) {
        super(context);

        int cellPx = model.getCellWidth();
        setLayoutParams(new AbsListView.LayoutParams(cellPx, cellPx));

        this.model = model;
        this.cellsCountInRow = model.getCellsCountInLine();
        this.position = position;
        this.paddingCell = new RelativeLayout(context);
        this.paddingCell.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        addView(paddingCell);

        int cellMarginPx = model.getCellMargin();
        if (isFirstColumn(position)) {
            if (isFirstRaw(position)) {
                setPadding(cellMarginPx, cellMarginPx, cellMarginPx / 2, cellMarginPx / 2);
            } else if (isLastRaw(position)) {
                setPadding(cellMarginPx, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx);
            } else {
                setPadding(cellMarginPx, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2);
            }
        } else if (isLastColumn(position)) {
            if (isFirstRaw(position)) {
                setPadding(cellMarginPx / 2, cellMarginPx, cellMarginPx, cellMarginPx / 2);
            } else if (isLastRaw(position)) {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx, cellMarginPx);
            } else {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx, cellMarginPx / 2);
            }
        } else {
            if (isFirstRaw(position)) {
                setPadding(cellMarginPx / 2, cellMarginPx, cellMarginPx / 2, cellMarginPx / 2);
            } else if (isLastRaw(position)) {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx);
            } else {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2);
            }
        }
    }

    @Override
    public String toString() {
        return "P " + position;
    }

    @Override
    public void setBackgroundColor(int color) {
        paddingCell.setBackgroundColor(color);
    }

    public Model getModel() {
        return model;
    }

    public int getPosition() {
        return position;
    }

    private boolean isFirstColumn(int position) {
        return position % cellsCountInRow == 0;
    }

    private boolean isLastColumn(int position) {
        return (position + 1) % cellsCountInRow == 0;
    }

    private boolean isFirstRaw(int position) {
        return position / cellsCountInRow == 0;
    }

    private boolean isLastRaw(int position) {
        return position / cellsCountInRow == cellsCountInRow - 1;
    }
}
