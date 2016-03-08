package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Cell {

    private Context context;
    private RelativeLayout cellView;
    private GradientDrawable background;
    private TextView numberView;
    private int position;

    public Cell(RelativeLayout cellView, int position) {
        this.context = cellView.getContext();
        this.cellView = cellView;
        this.position = position;

        RelativeLayout substrate = (RelativeLayout) cellView.findViewById(R.id.substrate);
        this.background = (GradientDrawable) substrate.getBackground();
        this.numberView = (TextView) cellView.findViewById(R.id.number);

        setBackgroundColor(R.color.defaultCell);
    }

    @Override
    public String toString() {
        return "p" + position + ":n" + numberView.getText();
    }

    public RelativeLayout getCellView() {
        return cellView;
    }

    public void setBackgroundColor(int color) {
        background.setColor(ContextCompat.getColor(context, color));
    }

    public void setTextColor(int color) {
        numberView.setTextColor(color);
    }

    public boolean hasNumber() {
        return getNumber() != 0;
    }

    public int getNumber() {
        int number = 0;
        try {
            String text = (String) numberView.getText();
            number = Integer.valueOf(text);
        } catch (NumberFormatException ignored) {
        }
        return number;
    }

    public void setNumber(int number) {
        numberView.setText(String.valueOf(number));
        switch (number) {
            case 0:
                setBackgroundColor(R.color.defaultCell);
                numberView.setText("");
                break;
            case 2:
                setBackgroundColor(R.color.cell_2);
                setTextColor(R.color.cellTextDark);
                break;
            case 4:
                setBackgroundColor(R.color.cell_4);
                setTextColor(R.color.cellTextDark);
                break;
            case 8:
                setBackgroundColor(R.color.cell_8);
                setTextColor(R.color.cellTextLight);
                break;
            case 16:
                setBackgroundColor(R.color.cell_16);
                setTextColor(R.color.cellTextLight);
                break;
            case 32:
                setBackgroundColor(R.color.cell_32);
                setTextColor(R.color.cellTextLight);
                break;
            case 64:
                setBackgroundColor(R.color.cell_64);
                setTextColor(R.color.cellTextLight);
                break;
            case 128:
                setBackgroundColor(R.color.cell_128);
                setTextColor(R.color.cellTextLight);
                break;
            case 256:
                setBackgroundColor(R.color.cell_256);
                setTextColor(R.color.cellTextLight);
                break;
            case 512:
                setBackgroundColor(R.color.cell_512);
                setTextColor(R.color.cellTextLight);
                break;
            case 1024:
                setBackgroundColor(R.color.cell_1024);
                setTextColor(R.color.cellTextLight);
                break;
            case 2048:
                setBackgroundColor(R.color.cell_2048);
                setTextColor(R.color.cellTextLight);
                break;
            default:
                setBackgroundColor(R.color.cell_more);
        }
    }

    public int getPosition() {
        return position;
    }

    public void startAnimation(Animation animation) {
        cellView.startAnimation(animation);
    }
}