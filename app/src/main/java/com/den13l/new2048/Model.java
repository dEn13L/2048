package com.den13l.new2048;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

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
    public static final String TAG = "DENI";

    private final int CELLS_COUNT_IN_LINE = 4;
    private final int INITIATED_CELLS = 5;
    private final int INITIAL_SCALE = 2;
    Comparator<Cell> straightComp = new Comparator<Cell>() {
        @Override
        public int compare(Cell a, Cell b) {
            return a.getPosition() - b.getPosition();
        }
    };

    Comparator<Cell> backComp = new Comparator<Cell>() {
        @Override
        public int compare(Cell a, Cell b) {
            return b.getPosition() - a.getPosition();
        }
    };
    private int cellWidth;
    private int cellMargin;
    private int boardWidth;

    public Model(int deviceWidth) {
        int idealCellWidth = deviceWidth / CELLS_COUNT_IN_LINE;

        this.cellMargin = idealCellWidth / 10;
        this.boardWidth = deviceWidth - 2 * cellMargin;
        this.cellWidth = boardWidth / CELLS_COUNT_IN_LINE;
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
        return (boardWidth - getCellMargin() * (CELLS_COUNT_IN_LINE + 1)) / CELLS_COUNT_IN_LINE;
    }

    public int getCellsCountInRow() {
        return CELLS_COUNT_IN_LINE;
    }

    public int getInitiatedCells() {
        return INITIATED_CELLS;
    }

    public int getInitialScale() {
        return INITIAL_SCALE;
    }

    private List<Value> getInitValues(List<Value> values) {
        List<Value> initValues = new ArrayList<>();
        for (Value value : values) {
            if (value.getNumber() != 0) {
                initValues.add(value);
            }
        }
        return initValues;
    }

    public List<Value> initValues(List<Value> values, int initCells) {
        int i = 0;
        List<Value> initValues = getInitValues(values);
        while (i < initCells) {
            Value value = initValue(values);
            if (!isValueInit(initValues, value)) {

                ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 0.5f, 0f, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(500);

                value.startAnimation(scaleAnimation);

                initValues.add(value);
                i++;
            }
        }
        return values;
    }

    public List<Value> initValues(List<Value> values) {
        return initValues(values, INITIATED_CELLS);
    }

    private boolean isValueInit(List<Value> values, Value value) {
        boolean init = false;
        for (Value v : values) {
            if (v.equals(value)) {
                init = true;
                break;
            }
        }
        return init;
    }

    private Value initValue(List<Value> values) {
        Value initValue = null;
        int random = new Random().nextInt(CELLS_COUNT_IN_LINE * CELLS_COUNT_IN_LINE);
        for (Value v : values) {
            int position = v.getPosition();
            if (random == position) {
                int pow = new Random().nextInt(INITIAL_SCALE) + 1;
                int number = (int) Math.pow(2, pow);
                initValue = v;
                initValue.setNumber(number);
                break;
            }
        }
        return initValue;
    }

    private Map<Integer, List<Value>> getRowValuesMap(List<Value> values) {
        Map<Integer, List<Value>> valuesMap = new HashMap<>();
        for (Value value : values) {
            int row = getRow(value.getPosition());
            List<Value> v = valuesMap.get(row);
            if (v == null) {
                v = new ArrayList<>();
                valuesMap.put(row, v);
            }
            v.add(value);
        }
        return valuesMap;
    }

    private Map<Integer, List<Value>> getColumnValuesMap(List<Value> values) {
        Map<Integer, List<Value>> valuesMap = new HashMap<>();
        for (Value value : values) {
            int row = getColumn(value.getPosition());
            List<Value> v = valuesMap.get(row);
            if (v == null) {
                v = new ArrayList<>();
                valuesMap.put(row, v);
            }
            v.add(value);
        }
        return valuesMap;
    }

    private ShiftAnimation createShiftAnimation(List<Value> sourceValues, ShiftDirection shiftDirection, SideShift sideShift) {
        ShiftAnimation shiftAnimation = new ShiftAnimation(sourceValues, shiftDirection);
        Map<Integer, List<Value>> valuesMap = null;

        switch (shiftDirection) {
            case LEFT:
                Collections.sort(sourceValues, straightComp);
                valuesMap = getRowValuesMap(sourceValues);
                break;
            case TOP:
                Collections.sort(sourceValues, straightComp);
                valuesMap = getColumnValuesMap(sourceValues);
                break;
            case RIGHT:
                Collections.sort(sourceValues, backComp);
                valuesMap = getRowValuesMap(sourceValues);
                break;
            case BOTTOM:
                Collections.sort(sourceValues, backComp);
                valuesMap = getColumnValuesMap(sourceValues);
                break;
            default:
                break;
        }

        Set<Map.Entry<Integer, List<Value>>> entries = valuesMap.entrySet();
        for (Map.Entry<Integer, List<Value>> entry : entries) {
            List<Value> values = entry.getValue();
            int shift = 0;
            for (Value value : values) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : values) {
                        if (v.getPosition() == sideShift.getDestPosition(value, shift)) {
                            destValue = v;
                            break;
                        }
                    }
                    if (destValue != null) {
                        TranslateAnimation tr = sideShift.getTranslateAnimation(shift);
                        tr.setDuration(300);

                        Shift shift1 = new Shift(value, destValue, tr);
                        shiftAnimation.addShift(shift1);
                    }
                }
            }
        }
        return shiftAnimation;
    }

    public void onSwipeLeft(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.LEFT, new SideShift() {
            @Override
            public int getDestPosition(Value value, int shift) {
                return value.getPosition() - shift;
            }

            @Override
            public TranslateAnimation getTranslateAnimation(int shift) {
                return new TranslateAnimation(0, -cellWidth * shift, 0, 0);
            }
        });
        shiftAnimation.setSwipeEndListener(swipeEndListener);
        shiftAnimation.start();
    }

    public void onSwipeTop(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.TOP, new SideShift() {
            @Override
            public int getDestPosition(Value value, int shift) {
                return value.getPosition() - shift * CELLS_COUNT_IN_LINE;
            }

            @Override
            public TranslateAnimation getTranslateAnimation(int shift) {
                return new TranslateAnimation(0, 0, 0, -cellWidth * shift);
            }
        });
        shiftAnimation.setSwipeEndListener(swipeEndListener);
        shiftAnimation.start();
    }

    public void onSwipeRight(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.RIGHT, new SideShift() {
            @Override
            public int getDestPosition(Value value, int shift) {
                return value.getPosition() + shift;
            }

            @Override
            public TranslateAnimation getTranslateAnimation(int shift) {
                return new TranslateAnimation(0, cellWidth * shift, 0, 0);
            }
        });
        shiftAnimation.setSwipeEndListener(swipeEndListener);
        shiftAnimation.start();
    }

    public void onSwipeBottom(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        ShiftAnimation shiftAnimation = createShiftAnimation(values, ShiftDirection.BOTTOM, new SideShift() {
            @Override
            public int getDestPosition(Value value, int shift) {
                return value.getPosition() + shift * CELLS_COUNT_IN_LINE;
            }

            @Override
            public TranslateAnimation getTranslateAnimation(int shift) {
                return new TranslateAnimation(0, 0, 0, cellWidth * shift);
            }
        });
        shiftAnimation.setSwipeEndListener(swipeEndListener);
        shiftAnimation.start();
    }

    private int getColumn(int position) {
        return position % CELLS_COUNT_IN_LINE;
    }

    private int getRow(int position) {
        return position / CELLS_COUNT_IN_LINE;
    }
}
