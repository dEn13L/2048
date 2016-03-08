package com.den13l.new2048;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Model {

    private final int CELLS_COUNT_IN_LINE = 4;
    private final int INITIATED_CELLS = 2;
    private final int INITIAL_SCALE = 2;

    private int cellPadding;
    private int cellWidth;
    private Comparator<Cell> straightComp = new Comparator<Cell>() {
        @Override
        public int compare(Cell a, Cell b) {
            return a.getPosition() - b.getPosition();
        }
    };
    private Comparator<Cell> backComp = new Comparator<Cell>() {
        @Override
        public int compare(Cell a, Cell b) {
            return b.getPosition() - a.getPosition();
        }
    };

    public Model(int deviceWidth) {
        int idealCellWidth = deviceWidth / CELLS_COUNT_IN_LINE;
        this.cellPadding = idealCellWidth / 10;
        int boardWidth = deviceWidth - 2 * cellPadding;
        this.cellWidth = boardWidth / CELLS_COUNT_IN_LINE;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellPadding() {
        return cellPadding;
    }

    public int getCellsCountInLine() {
        return CELLS_COUNT_IN_LINE;
    }

    public void initCells(List<Cell> cells) {
        initCells(cells, INITIATED_CELLS);
    }

    public void initCells(List<Cell> cells, int initiatedCells) {
        int i = 0;
        while (i < initiatedCells) {
            List<Cell> notInitCells = getNotInitCells(cells);
            Cell cell = initCell(notInitCells);
            if (cell != null) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 0.5f, 0f, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(300);
                cell.startAnimation(scaleAnimation);
                i++;
            }
        }
    }

    private List<Cell> getNotInitCells(List<Cell> cells) {
        List<Cell> notInitCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.hasNumber()) {
                notInitCells.add(cell);
            }
        }
        return notInitCells;
    }

    private Cell initCell(List<Cell> cells) {
        Random random = new Random();
        for (Cell cell : cells) {
            if (random.nextInt(cells.size()) == 0) {
                int pow = random.nextInt(INITIAL_SCALE) + 1;
                int number = (int) Math.pow(2, pow);
                cell.setNumber(number);
                return cell;
            }
        }
        return null;
    }

    public void onSwipeLeft(List<Cell> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.LEFT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeTop(List<Cell> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.TOP);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeRight(List<Cell> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.RIGHT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeBottom(List<Cell> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.BOTTOM);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    private ShiftAnimation createShiftAnimation(List<Cell> sourceCells, ShiftDirection direction) {
        ShiftAnimation shiftAnimation = new ShiftAnimation(sourceCells);
        Map<Integer, List<Cell>> cellsMap = null;
        switch (direction) {
            case LEFT:
                Collections.sort(sourceCells, straightComp);
                cellsMap = getRowCellsMap(sourceCells);
                break;
            case TOP:
                Collections.sort(sourceCells, straightComp);
                cellsMap = getColumnCellsMap(sourceCells);
                break;
            case RIGHT:
                Collections.sort(sourceCells, backComp);
                cellsMap = getRowCellsMap(sourceCells);
                break;
            case BOTTOM:
                Collections.sort(sourceCells, backComp);
                cellsMap = getColumnCellsMap(sourceCells);
                break;
            default:
                break;
        }
        Set<Map.Entry<Integer, List<Cell>>> entries = cellsMap.entrySet();
        for (Map.Entry<Integer, List<Cell>> entry : entries) {
            List<Cell> cells = entry.getValue();
            LineShift lineShift = new LineShift(cells, CELLS_COUNT_IN_LINE, getCellWidth());
            shiftAnimation.addLineShift(lineShift);
        }
        Log.d(GameActivity.TAG, shiftAnimation.toString());
        return shiftAnimation;
    }

    private Map<Integer, List<Cell>> getRowCellsMap(List<Cell> cells) {
        Map<Integer, List<Cell>> cellsMap = new HashMap<>();
        for (Cell cell : cells) {
            int row = getRow(cell.getPosition());
            List<Cell> c = cellsMap.get(row);
            if (c == null) {
                c = new ArrayList<>();
                cellsMap.put(row, c);
            }
            c.add(cell);
        }
        return cellsMap;
    }

    private int getRow(int position) {
        return position / CELLS_COUNT_IN_LINE;
    }

    private Map<Integer, List<Cell>> getColumnCellsMap(List<Cell> cells) {
        Map<Integer, List<Cell>> cellsMap = new HashMap<>();
        for (Cell cell : cells) {
            int column = getColumn(cell.getPosition());
            List<Cell> c = cellsMap.get(column);
            if (c == null) {
                c = new ArrayList<>();
                cellsMap.put(column, c);
            }
            c.add(cell);
        }
        return cellsMap;
    }

    private int getColumn(int position) {
        return position % CELLS_COUNT_IN_LINE;
    }
}
