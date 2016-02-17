package com.den13l.new2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Model {
    public static final String TAG = "DENI";

    private final int CELLS_COUNT_IN_ROW = 4;
    private final int INITIATED_CELLS = 2;
    private final int INITIAL_SCALE = 2;

    private int cellWidth;
    private int cellMargin;
    private int boardWidth;

    public Model(int deviceWidth) {
        int idealCellWidth = deviceWidth / CELLS_COUNT_IN_ROW;

        this.cellMargin = idealCellWidth / 10;
        this.boardWidth = deviceWidth - 2 * cellMargin;
        this.cellWidth = boardWidth / CELLS_COUNT_IN_ROW;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellMargin() {
        return cellMargin;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getInnerCellWidth() {
        return (boardWidth - getCellMargin() * (CELLS_COUNT_IN_ROW + 1)) / CELLS_COUNT_IN_ROW;
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

    public List<Cell> initValues(List<Cell> cells) {
        int i = 0;
        List<Cell> initCell = new ArrayList<>();
        while (i < INITIATED_CELLS) {
            Cell cell = initCell(cells);
            if (!isCellInit(initCell, cell)) {
                initCell.add(cell);
                i++;
            }
        }
        return initCell;
    }

    private boolean isCellInit(List<Cell> cells, Cell cell) {
        boolean init = false;
        for (Cell c : cells) {
            if (c.equals(cell)) {
                init = true;
                break;
            }
        }
        return init;
    }

    private Cell initCell(List<Cell> cells) {
        Cell initCell = null;
        int random = new Random().nextInt(CELLS_COUNT_IN_ROW * CELLS_COUNT_IN_ROW);
        for (Cell c : cells) {
            int position = c.getPosition();
            if (random == position) {
                int pow = new Random().nextInt(INITIAL_SCALE) + 1;
                int number = (int) Math.pow(2, pow);
                initCell = c;
                if (initCell instanceof Value) {
                    ((Value) initCell).setNumber(number);
                }
                break;
            }
        }
        return initCell;
    }
}
