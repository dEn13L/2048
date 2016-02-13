package com.den13l.new2048;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Model {

    private final int CELLS_COUNT_IN_ROW = 4;
    private final int INITIATED_CELLS = 2;
    private final int INITIAL_SCALE = 2;

    private Context context;
    private int widthPx;
    private int cellPx;

    public Model(Context context, int widthPx) {
        this.context = context;
        this.widthPx = widthPx;
        this.cellPx = widthPx / CELLS_COUNT_IN_ROW;
    }

    public int getCellPx() {
        return cellPx;
    }

    public int getCellMarginPx() {
        return cellPx / 10;
    }

    public int getInnerCellPx() {
        return (widthPx - getCellMarginPx() * (CELLS_COUNT_IN_ROW + 1)) / CELLS_COUNT_IN_ROW;
    }

    public int getCellsCountInRow() {
        return CELLS_COUNT_IN_ROW;
    }

    public int getInitiatedCells() {
        return INITIATED_CELLS;
    }

    public int getInitialScale() {
        return INITIAL_SCALE;
    }

    public List<Cell> initCells(ArrayList<Cell> cells) {
        int i = 0;
        ArrayList<Cell> initCells = new ArrayList<>();
        while(i < INITIATED_CELLS) {
            Cell initCell = initCell(cells);
            if (!isCellInit(initCells, initCell)) {
                initCells.add(initCell);
                i++;
            }
        }
        return initCells;
    }

    private boolean isCellInit(ArrayList<Cell> initCells, Cell initCell) {
        boolean init = false;
        for (Cell cell : initCells) {
            if (cell.equals(initCell)) {
                init = true;
                break;
            }
        }
        return init;
    }

    private Cell initCell(ArrayList<Cell> cells) {
        Cell initCell = null;
        int random = new Random().nextInt(CELLS_COUNT_IN_ROW * CELLS_COUNT_IN_ROW);
        for (Cell cell : cells) {
            int i = cell.getI();
            int j = cell.getJ();
            int position = i * CELLS_COUNT_IN_ROW + j;
            if (random == position) {
                int pow = new Random().nextInt(INITIAL_SCALE) + 1;
                int value = (int) Math.pow(2, pow);
                cell.setText(String.valueOf(value));
                initCell = cell;
                break;
            }
        }
        return initCell;
    }
}
