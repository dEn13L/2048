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

    private void sortValuesMap(Map<Integer, List<Value>> valuesMap, Comparator comparator) {
        Set<Map.Entry<Integer, List<Value>>> entries = valuesMap.entrySet();
        for (Map.Entry<Integer, List<Value>> entry : entries) {
            Collections.sort(entry.getValue(), comparator);
        }
    }

    private ShiftAnimation createShiftAnimation(List<Value> sourceValues, ShiftDirection shiftDirection, SideShift sideShift) {
        ShiftAnimation shiftAnimation = new ShiftAnimation(sourceValues);
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

    private boolean isColumn(int position, int column) {
        return position % CELLS_COUNT_IN_LINE == column;
    }

    private boolean isLine(int position, int line) {
        return position / CELLS_COUNT_IN_LINE == line;
    }

    private class Shift {

        private Value sourceValue;
        private Value destValue;
        private TranslateAnimation tr;

        Shift(Value sourceValue, Value destValue, TranslateAnimation tr) {
            this.sourceValue = sourceValue;
            this.destValue = destValue;
            this.tr = tr;
        }

        public Value getSourceValue() {
            return sourceValue;
        }

        public Value getDestValue() {
            return destValue;
        }

        public TranslateAnimation getTr() {
            return tr;
        }
    }

    private class ShiftAnimation {

        private List<Shift> shifts;
        private List<Value> values;
        private SwipeEndListener swipeEndListener;

        public ShiftAnimation(List<Value> values) {
            this.shifts = new ArrayList<>();
            this.values = values;
        }

        public void addShift(Shift shift) {
            shifts.add(shift);
        }

        public void setSwipeEndListener(SwipeEndListener swipeEndListener) {
            this.swipeEndListener = swipeEndListener;
        }

        public void start() {
            TranslateAnimation tr = null;
            for (Shift shift : shifts) {
                Value sourceValue = shift.getSourceValue();
                tr = shift.getTr();
                sourceValue.startAnimation(tr);
            }
            if (tr != null && swipeEndListener != null) {
                ShiftListener shiftListener = new ShiftListener(shifts);
                shiftListener.setSwipeEndListener(values, swipeEndListener);
                tr.setAnimationListener(shiftListener);
            }
        }
    }

    private class ShiftListener implements Animation.AnimationListener {

        private List<Shift> shifts;
        private List<Value> values;
        private SwipeEndListener swipeEndListener;

        private List<Value> sourceZero;
        private List<Value> sourceShifted;
        private List<Value> destShifted;

        public ShiftListener(List<Shift> shifts) {
            this.shifts = shifts;
            this.sourceZero = new ArrayList<>();
            this.sourceShifted = new ArrayList<>();
            this.destShifted = new ArrayList<>();
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            for (Shift shift : shifts) {
                Value destValue = shift.getDestValue();
                Value sourceValue = shift.getSourceValue();

                destShifted.add(destValue);
                sourceShifted.add(sourceValue);

                destValue.setNumber(sourceValue.getNumber());
            }
            for (Value sourceValue : sourceShifted) {
                boolean found = false;
                for (Value destValue : destShifted) {
                    if (sourceValue.equals(destValue)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    sourceZero.add(sourceValue);
                }
            }
            for (Value value : sourceZero) {
                value.setNumber(0);
            }
            if (swipeEndListener != null) {
                swipeEndListener.onSwiped(values);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        public void setSwipeEndListener(List<Value> values, SwipeEndListener swipeEndListener) {
            this.values = values;
            this.swipeEndListener = swipeEndListener;
        }
    }

}
