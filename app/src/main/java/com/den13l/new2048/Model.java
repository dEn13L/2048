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

    public List<Value> initValues(List<Value> values) {
        int i = 0;
        List<Value> initValues = new ArrayList<>();
        while (i < INITIATED_CELLS) {
            Value value = initValue(values);
            if (!isValueInit(initValues, value)) {
                initValues.add(value);
                i++;
            }
        }
        return initValues;
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

    public void onSwipeLeft(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        Map<Integer, List<Value>> rowValuesMap = getRowValuesMap(values);
        Set<Map.Entry<Integer, List<Value>>> rowEntries = rowValuesMap.entrySet();
        ShiftListener sshiftListener = null;
        for (Map.Entry<Integer, List<Value>> rowEntry : rowEntries) {
            List<Value> rowValues = rowEntry.getValue();
            Collections.sort(rowValues, straightComp);
            Log.d(TAG, "rowValues: " + rowValues);
            int shift = 0;
            for (Value value : rowValues) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : rowValues) {
                        if (v.getPosition() == value.getPosition() - shift) {
                            destValue = v;
                            break;
                        }
                    }
                    Log.d(TAG, "sourceValue: " + value);
                    Log.d(TAG, "destValue: " + destValue);

                    AnimationSet set = new AnimationSet(false);
                    TranslateAnimation tr = new TranslateAnimation(0, -cellWidth * shift, 0, 0);
                    tr.setDuration(500);
                    set.addAnimation(tr);
                    ShiftListener shiftListener = new ShiftListener(value, destValue);
                    set.setAnimationListener(shiftListener);
                    if (destValue != null) {
                        value.startAnimation(set);
                        sshiftListener = shiftListener;
                    }
                }
            }
        }
        if (sshiftListener != null) {
            sshiftListener.setSwipeEndListener(swipeEndListener, values);
        }
    }

    public void onSwipeTop(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        Map<Integer, List<Value>> columnValuesMap = getColumnValuesMap(values);
        Set<Map.Entry<Integer, List<Value>>> columnEntries = columnValuesMap.entrySet();
        ShiftListener sshiftListener = null;
        for (Map.Entry<Integer, List<Value>> columnEntry : columnEntries) {
            List<Value> columnValues = columnEntry.getValue();
            Collections.sort(columnValues, straightComp);
            Log.d(TAG, "columnValues: " + columnValues);
            int shift = 0;
            for (Value value : columnValues) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : columnValues) {
                        if (v.getPosition() == value.getPosition() - shift * CELLS_COUNT_IN_LINE) {
                            destValue = v;
                            break;
                        }
                    }
                    Log.d(TAG, "sourceValue: " + value);
                    Log.d(TAG, "destValue: " + destValue);

                    AnimationSet set = new AnimationSet(false);
                    TranslateAnimation tr = new TranslateAnimation(0, 0, 0, -cellWidth * shift);
                    tr.setDuration(500);
                    set.addAnimation(tr);
                    ShiftListener shiftListener = new ShiftListener(value, destValue);
                    set.setAnimationListener(shiftListener);
                    if (destValue != null) {
                        value.startAnimation(set);
                        sshiftListener = shiftListener;
                    }
                }
            }
        }
        if (sshiftListener != null) {
            sshiftListener.setSwipeEndListener(swipeEndListener, values);
        }
    }

    public void onSwipeRight(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        Map<Integer, List<Value>> rowValuesMap = getRowValuesMap(values);
        Set<Map.Entry<Integer, List<Value>>> rowEntries = rowValuesMap.entrySet();
        ShiftListener sshiftListener = null;
        for (Map.Entry<Integer, List<Value>> rowEntry : rowEntries) {
            List<Value> rowValues = rowEntry.getValue();
            Collections.sort(rowValues, backComp);
            Log.d(TAG, "rowValues: " + rowValues);
            int shift = 0;
            for (Value value : rowValues) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : rowValues) {
                        if (v.getPosition() == value.getPosition() + shift) {
                            destValue = v;
                            break;
                        }
                    }
                    Log.d(TAG, "sourceValue: " + value);
                    Log.d(TAG, "destValue: " + destValue);

                    AnimationSet set = new AnimationSet(false);
                    TranslateAnimation tr = new TranslateAnimation(0, cellWidth * shift, 0, 0);
                    tr.setDuration(500);
                    set.addAnimation(tr);
                    ShiftListener shiftListener = new ShiftListener(value, destValue);
                    set.setAnimationListener(shiftListener);
                    if (destValue != null) {
                        value.startAnimation(set);
                        sshiftListener = shiftListener;
                    }
                }
            }
        }
        if (sshiftListener != null) {
            sshiftListener.setSwipeEndListener(swipeEndListener, values);
        }
    }

    public void onSwipeBottom(List<Value> values, SwipeEndListener swipeEndListener) {
        Log.d(TAG, "values: " + values);

        Map<Integer, List<Value>> columnValuesMap = getColumnValuesMap(values);
        Set<Map.Entry<Integer, List<Value>>> columnEntries = columnValuesMap.entrySet();
        ShiftListener sshiftListener = null;
        for (Map.Entry<Integer, List<Value>> columnEntry : columnEntries) {
            List<Value> columnValues = columnEntry.getValue();
            Collections.sort(columnValues, backComp);
            Log.d(TAG, "columnValues: " + columnValues);
            int shift = 0;
            for (Value value : columnValues) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : columnValues) {
                        if (v.getPosition() == value.getPosition() + shift * CELLS_COUNT_IN_LINE) {
                            destValue = v;
                            break;
                        }
                    }
                    Log.d(TAG, "sourceValue: " + value);
                    Log.d(TAG, "destValue: " + destValue);

                    AnimationSet set = new AnimationSet(false);
                    TranslateAnimation tr = new TranslateAnimation(0, 0, 0, cellWidth * shift);
                    tr.setDuration(500);
                    set.addAnimation(tr);
                    ShiftListener shiftListener = new ShiftListener(value, destValue);
                    set.setAnimationListener(shiftListener);
                    if (destValue != null) {
                        value.startAnimation(set);
                        sshiftListener = shiftListener;
                    }
                }
            }
        }
        if (sshiftListener != null) {
            sshiftListener.setSwipeEndListener(swipeEndListener, values);
        }
    }

    private class ShiftListener implements Animation.AnimationListener {

        private Value sourceValue;
        private Value destValue;
        private SwipeEndListener swipeEndListener;
        private List<Value> values;

        public ShiftListener(Value sourceValue, Value destValue) {
            this.sourceValue = sourceValue;
            this.destValue = destValue;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            destValue.setNumber(sourceValue.getNumber());
            sourceValue.setNumber(0);
            if (swipeEndListener != null) {
                swipeEndListener.onSwiped(values);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        public void setSwipeEndListener(SwipeEndListener swipeEndListener, List<Value> values) {
            this.swipeEndListener = swipeEndListener;
            this.values = values;
        }
    }

    private int getColumn(int position) {
        return position % CELLS_COUNT_IN_LINE;
    }

    private int getRow(int position) {
        return position / CELLS_COUNT_IN_LINE;
    }

    private boolean isColumn(int position, int column) {
        return position % CELLS_COUNT_IN_LINE == column;
    }

    private boolean isLine(int position, int line) {
        return position / CELLS_COUNT_IN_LINE == line;
    }

}
