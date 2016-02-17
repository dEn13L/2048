package com.den13l.new2048;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by erdenierdyneev on 14.02.16.
 */
public class Value extends Cell {

    private TextView textView;

    public Value(Value value) {
        this(value.getContext(), value.getPosition(), value.getModel());
    }

    public Value(Context context, int position, Model model) {
        super(context, position, model);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(CENTER_VERTICAL);

        textView = new TextView(context);
        textView.setLayoutParams(params);
        paddingCell.addView(textView);
    }

    @Override
    public String toString() {
        return "P" + position + ":N" + textView.getText();
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
            default:
                setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    public int getNumber() {
        return Integer.parseInt((String) textView.getText());

    }
}
