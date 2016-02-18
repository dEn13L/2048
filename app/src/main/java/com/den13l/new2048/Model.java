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
    Comparator<Cell> comp = new Comparator<Cell>() {
        @Override
        public int compare(Cell a, Cell b) {
            return a.getPosition() - b.getPosition();
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

    public ArrayList<Value> onSwipeLeft(Context context, ArrayList<Value> values) {
        ArrayList<Value> modValues = (ArrayList<Value>) values.clone();

        Log.d(TAG, "originValues: " + values);

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

        Set<Map.Entry<Integer, List<Value>>> entries = valuesMap.entrySet();
        for (Map.Entry<Integer, List<Value>> entry : entries) {
            List<Value> lineValues = entry.getValue();
            Collections.sort(lineValues, comp);
            int shift = 0;
            for (Value value : lineValues) {
                if (value.getNumber() == 0) {
                    shift++;
                } else if (shift > 0) {
                    Value destValue = null;
                    for (Value v : lineValues) {
                        if (v.getPosition() == value.getPosition() - shift) {
                            destValue = v;
                            break;
                        }
                    }

                    AnimationSet set = new AnimationSet(false);
                    TranslateAnimation tr = new TranslateAnimation(0, -cellWidth * shift, 0, 0);
                    tr.setDuration(1_000);
                    set.addAnimation(tr);
                    set.setAnimationListener(new ShiftListener(value, destValue));
                    if (destValue != null) {
                        for (Value modValue : modValues) {
                            int modValuePosition = modValue.getPosition();
                            if (modValuePosition == destValue.getPosition()) {
//                                modValue.setNumber(value.getNumber());
                            } else if (modValuePosition == value.getPosition()) {
//                                modValue.setNumber(0);
                            }
                        }
                        value.startAnimation(set);
                    }
                }
            }
        }
        Log.d(TAG, "modValues   : " + modValues);
        Log.d(TAG, "values      : " + values);
        return modValues;
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

    private class ShiftListener implements Animation.AnimationListener {

        private Value sourceValue;
        private Value destValue;

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
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
