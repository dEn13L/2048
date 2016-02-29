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

    private final String TEXT_COLOR = "#776E65";
    private final String BRIGHT_TEXT_COLOR = "#f9f6f2";
    private final String TILE_COLOR = "#eee4da";
    private final String TILE_GOLD_COLOR = "#edc22e";

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
    private int cellWidth;

    public Model(int deviceWidth) {
        int idealCellWidth = deviceWidth / CELLS_COUNT_IN_LINE;
        this.cellMargin = idealCellWidth / 10;
        int boardWidth = deviceWidth - 2 * cellMargin;
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

    public void initCells(List<CellView> cells) {
        initCells(cells, INITIATED_CELLS);
    }

    public void initCells(List<CellView> cells, int initiatedCells) {
        int i = 0;
        while (i < initiatedCells) {
            List<CellView> notInitCells = getNotInitCells(cells);
            CellView cell = initCell(notInitCells);
            if (cell != null) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 0.5f, 0f, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(300);
                cell.startAnimation(scaleAnimation);
                i++;
            }
        }
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

    public void onSwipeLeft(List<CellView> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.LEFT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeTop(List<CellView> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.TOP);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeRight(List<CellView> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.RIGHT);
        shiftAnimation.setShiftEndListener(shiftEndListener);
        shiftAnimation.start();
    }

    public void onSwipeBottom(List<CellView> cells, ShiftEndListener shiftEndListener) {
        ShiftAnimation shiftAnimation = createShiftAnimation(cells, ShiftDirection.BOTTOM);
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
            int column = getColumn(cell.getPosition());
            List<CellView> c = cellsMap.get(column);
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
