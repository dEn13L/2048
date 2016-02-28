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

    Comparator<CellView> straightComp = new Comparator<CellView>() {
        @Override
        public int compare(CellView a, CellView b) {
            return a.getPosition() - b.getPosition();
        }
    };
    Comparator<CellView> backComp = new Comparator<CellView>() {
        @Override
        public int compare(CellView a, CellView b) {
            return b.getPosition() - a.getPosition();
        }
    };
    private int cellMargin;
    private int boardWidth;
    private int cellWidth;

    public Model(int deviceWidth) {
        int idealCellWidth = deviceWidth / CELLS_COUNT_IN_LINE;
        this.cellMargin = idealCellWidth / 10;
        this.boardWidth = deviceWidth - 2 * cellMargin; // except right and left margin
        this.cellWidth = boardWidth / CELLS_COUNT_IN_LINE;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellMargin() {
        return cellMargin;
    }

    public int getCellsCountInLine() {
        return CELLS_COUNT_IN_LINE;
    }

    public List<CellView> initCells(List<CellView> cells) {
        return initCells(cells, INITIATED_CELLS);
    }

    public List<CellView> initCells(List<CellView> cells, int initiatedCells) {
        int i = 0;
        List<CellView> notInitCells = getNotInitCells(cells);
        while (i < initiatedCells) {
            CellView cell = initCell(notInitCells);
            if (cell != null) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 0.5f, 0f, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(500);
                cell.startAnimation(scaleAnimation);
                i++;
            }
        }
        return cells;
    }

    private List<CellView> getNotInitCells(List<CellView> cells) {
        List<CellView> notInitCells = new ArrayList<>();
        for (CellView cell : cells) {
            if (!cell.hasNumber()) {
                notInitCells.add(cell);
            }
        }
        return notInitCells;
    }

    private CellView initCell(List<CellView> cells) {
        Random random = new Random();
        for (CellView cell : cells) {
            if (random.nextInt(cells.size()) == 0) {
                int pow = random.nextInt(INITIAL_SCALE) + 1;
                int number = (int) Math.pow(2, pow);
                cell.setNumber(number);
                return cell;
            }
        }
        return null;
    }

    public void onSwipeLeft(List<CellView> values, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.LEFT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeTop(List<CellView> values, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.TOP);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeRight(List<CellView> values, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.RIGHT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeBottom(List<CellView> values, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.BOTTOM);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    private ShiftAnimation createShiftAnimation(List<CellView> sourceCells, ShiftDirection direction) {
        ShiftAnimation shiftAnimation = new ShiftAnimation(sourceCells);
        Map<Integer, List<CellView>> cellsMap = null;
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
        Set<Map.Entry<Integer, List<CellView>>> entries = cellsMap.entrySet();
        for (Map.Entry<Integer, List<CellView>> entry : entries) {
            List<CellView> cells = entry.getValue();
            LineShift lineShift = new LineShift(cells, CELLS_COUNT_IN_LINE, getCellWidth());
            shiftAnimation.addLineShift(lineShift);
        }
        Log.d(GameActivity.TAG, shiftAnimation.toString());
        return shiftAnimation;
    }

    private Map<Integer, List<CellView>> getRowCellsMap(List<CellView> cells) {
        Map<Integer, List<CellView>> cellsMap = new HashMap<>();
        for (CellView cell : cells) {
            int row = getRow(cell.getPosition());
            List<CellView> c = cellsMap.get(row);
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

    private Map<Integer, List<CellView>> getColumnCellsMap(List<CellView> cells) {
        Map<Integer, List<CellView>> cellsMap = new HashMap<>();
        for (CellView cell : cells) {
            int row = getColumn(cell.getPosition());
            List<CellView> c = cellsMap.get(row);
            if (c == null) {
                c = new ArrayList<>();
                cellsMap.put(row, c);
            }
            c.add(cell);
        }
        return cellsMap;
    }

    private int getColumn(int position) {
        return position % CELLS_COUNT_IN_LINE;
    }
}
