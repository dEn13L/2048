package com.den13l.new2048;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Model {
    public static final String TAG = "DENI";

    private final int CELLS_COUNT_IN_ROW = 4;
    private final int INITIATED_CELLS = 5;
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

    public List<Cell> initCells(List<Cell> cells) {
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

    public void onSwipeLeft(Context context, List<Cell> initCells, final List<Cell> originCells) {
        Map<Integer, List<Cell>> cellsMap = new HashMap<>();
        for (int i = 0; i < CELLS_COUNT_IN_ROW; i++) {
            for (Cell cell : initCells) {
                int position = cell.getPosition();
                if (isLine(position, i)) {
                    List<Cell> cells = cellsMap.get(i);
                    if (cells == null) {
                        cells = new ArrayList<>();
                        cellsMap.put(i, cells);
                    }
                    cells.add(cell);
                }
            }
        }

        Log.d(TAG, "cellsMap: " + cellsMap);

        Set<Integer> keys = cellsMap.keySet();
        for (Integer key : keys) {
            Log.d(TAG, "key: " + key);
            List<Cell> cells = cellsMap.get(key);
            Comparator<Cell> comp = new Comparator<Cell>() {
                @Override
                public int compare(Cell a, Cell b) {
                    return a.getPosition() - b.getPosition();
                }
            };
            Collections.sort(cells, comp);
            int shift = 0;
            int currentColumn = 0;
            for (final Cell cell : cells) {
                final int position = cell.getPosition();
                final int number = ((Value) cell).getNumber();
                Log.d(TAG, "    cell: " + cell);
                for (int i = currentColumn; i < CELLS_COUNT_IN_ROW; i++) {
                    currentColumn++;
                    if (isColumn(position, i)) {
                        final int finalShift = shift;
                        Log.d(TAG, "    finalShift: " + finalShift);
                        AnimationSet animationSet = new AnimationSet(false);
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, -cellWidth * shift, 0, 0);
                        translateAnimation.setDuration(2000);
                        animationSet.addAnimation(translateAnimation);
                        animationSet.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (Cell c : originCells) {
                                    if (c.getPosition() == position - finalShift) {
                                        ((Value) c).setNumber(number);
                                        ((Value) cell).setNumber(0);
                                        Log.d(TAG, "    c: " + c.toString());
                                        Log.d(TAG, "    cell: " + c.toString());
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        if (shift > 0) {
                            cell.startAnimation(animationSet);
                        }
                        break;
                    } else {
                        shift++;
                    }
                }
            }
        }
    }



    public void onSwipeTop(Context context, List<Cell> cellList) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_top);
        for (Cell cell : cellList) {
            cell.startAnimation(animation);
        }
    }

    public void onSwipeRight(Context context, List<Cell> cellList) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_right);
        for (Cell cell : cellList) {
            cell.startAnimation(animation);
        }
    }

    public void onSwipeBottom(Context context, List<Cell> cellList) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_bottom);
        for (Cell cell : cellList) {
            cell.startAnimation(animation);
        }
    }

    private boolean isColumn(int position, int column) {
        return position % CELLS_COUNT_IN_ROW == column;
    }

    private boolean isLine(int position, int line) {
        return position / CELLS_COUNT_IN_ROW == line;
    }
}
