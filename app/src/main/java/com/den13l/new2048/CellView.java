package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class CellView extends RelativeLayout {

    public static final String CELL_COLOR = "#bbada0";

    private GradientDrawable background;
    private TextView textView;
    private int position;

    public CellView(Context context, int position) {
        super(context);

        this.background = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.cornered_rectangle);
        this.position = position;

        RelativeLayout substrate = new RelativeLayout(context);
        substrate.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(CENTER_VERTICAL);

        this.textView = new TextView(context);
        this.textView.setLayoutParams(params);
        this.textView.setTextSize(35);
        this.textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

        addView(substrate);
        substrate.addView(textView);
        substrate.setBackground(background);
        setBackgroundColor(CELL_COLOR);
    }

    @Override
    public String toString() {
        return "p" + position + ":n" + textView.getText();
    }

    @Override
    public void setBackgroundColor(int color) {
        background.setColor(color);
    }

    public void setBackgroundColor(String color) {
        setBackgroundColor(Color.parseColor(color));
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setTextColor(String color) {
        setTextColor(Color.parseColor(color));
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
                setBackgroundColor("#eee4da");
                setTextColor("#776E65");
                break;
            case 4:
                setBackgroundColor("#eee1c9");
                setTextColor("#776E65");
                break;
            case 8:
                setBackgroundColor("#f3b27a");
                setTextColor("#f9f6f2");
                break;
            case 16:
                setBackgroundColor("#f69664");
                setTextColor("#f9f6f2");
                break;
            case 32:
                setBackgroundColor("#ff3333");
                setTextColor("#f9f6f2");
                break;
            case 64:
                setBackgroundColor("#f75f3b");
                setTextColor("#f9f6f2");
                break;
            case 128:
                setBackgroundColor("#edd073");
                setTextColor("#f9f6f2");
                break;
            case 256:
                setBackgroundColor("#edcc62");
                setTextColor("#f9f6f2");
                break;
            case 512:
                setBackgroundColor("#edc950");
                setTextColor("#f9f6f2");
                break;
            case 1024:
                setBackgroundColor("#edc53f");
                setTextColor("#f9f6f2");
                break;
            case 2048:
                setBackgroundColor("#edc22e");
                setTextColor("#f9f6f2");
                break;
            default:
                setBackgroundColor("#DDDDDD");
        }
    }

    public int getPosition() {
        return position;
    }
}
