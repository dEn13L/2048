package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class CellView extends RelativeLayout {

    public static final String CELL_COLOR = "#BBBBBB";

    private RelativeLayout substrate;
    private TextView textView;
    private Cell cell;

    public CellView(Context context, int position) {
        super(context);

        cell = new Cell(position);
        substrate = new RelativeLayout(context);
        substrate.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(CENTER_VERTICAL);

        textView = new TextView(context);
        textView.setLayoutParams(params);

        addView(substrate);
        substrate.addView(textView);
    }

    @Override
    public String toString() {
        return "P" + cell.getPosition() + ":N" + textView.getText();
    }

    @Override
    public void setBackgroundColor(int color) {
        substrate.setBackgroundColor(color);
    }

    public void setNumber(int number) {
        cell.setNumber(number);
        textView.setText(String.valueOf(number));
        switch (number) {
            case 0:
                setBackgroundColor(Color.TRANSPARENT);
                textView.setText("");
                break;
            case 2:
                setBackgroundColor(Color.parseColor("#EFE6E6"));
                break;
            case 4:
                setBackgroundColor(Color.parseColor("#CAAFAF"));
                break;
            case 8:
                setBackgroundColor(Color.parseColor("#C0AFAF"));
                break;
            default:
                setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
    }

    public boolean hasNumber() {
        return cell.hasNumber();
    }

    public int getNumber() {
        return cell.getNumber();
    }

    public int getPosition() {
        return cell.getPosition();
    }
}
