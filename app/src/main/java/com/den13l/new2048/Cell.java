package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Cell extends LinearLayout {

    private int i;
    private int j;
    private TextView textView;
    private RelativeLayout relativeLayout;

    public Cell(Context context, int i, int j, int cellMarginPx, int cellsCountInRow) {
        super(context);

        this.i = i;
        this.j = j;
        this.textView = new TextView(context);
        this.relativeLayout = new RelativeLayout(context);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.relativeLayout.setLayoutParams(lParams);
        this.relativeLayout.setBackgroundColor(Color.parseColor("#BBBBBB"));

        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rParams.addRule(RelativeLayout.CENTER_VERTICAL);
        this.textView.setLayoutParams(rParams);
        this.relativeLayout.addView(textView);
        addView(relativeLayout);

        if (j == 0) {
            if (i == 0) {
                setPadding(cellMarginPx, cellMarginPx, cellMarginPx / 2, cellMarginPx / 2);
            } else if (i == cellsCountInRow - 1) {
                setPadding(cellMarginPx, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx);
            } else {
                setPadding(cellMarginPx, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2);
            }
        } else if (j == cellsCountInRow - 1) {
            if (i == 0) {
                setPadding(cellMarginPx / 2, cellMarginPx, cellMarginPx, cellMarginPx / 2);
            } else if (i == cellsCountInRow - 1) {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx, cellMarginPx);
            } else {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx, cellMarginPx / 2);
            }
        } else {
            if (i == 0) {
                setPadding(cellMarginPx / 2, cellMarginPx, cellMarginPx / 2, cellMarginPx / 2);
            } else if (i == cellsCountInRow) {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx);
            } else {
                setPadding(cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2, cellMarginPx / 2);
            }
        }
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
