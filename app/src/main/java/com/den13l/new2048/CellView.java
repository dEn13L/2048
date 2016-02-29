package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
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
    private int position;

    public CellView(Context context, int position) {
        super(context);

        this.position = position;
        this.substrate = new RelativeLayout(context);
        this.substrate.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(CENTER_VERTICAL);

        this.textView = new TextView(context);
        this.textView.setLayoutParams(params);

        addView(substrate);
        substrate.addView(textView);
    }

    @Override
    public String toString() {
        return "P" + position + ":N" + textView.getText();
    }

    @Override
    public void setBackgroundColor(int color) {
        substrate.setBackgroundColor(color);
    }

    public boolean hasNumber() {
        return getNumber() != 0;
    }

    public int getNumber() {
        int number = 0;
        try {
            String text = (String) textView.getText();
            number = Integer.valueOf(text);
        } catch (NumberFormatException ignored) {
        }
        return number;
    }

    public void setNumber(int number) {
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

    public int getPosition() {
        return position;
    }
}
