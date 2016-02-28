package com.den13l.new2048;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class Utils {

    public static int getDeviceWidth(Activity activity) {
        return getDisplayMetrics(activity).widthPixels;
    }

    public static int getDeviceHeight(Activity activity) {
        return getDisplayMetrics(activity).heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static int dpToPx(Context context, int dp) {
        return Math.round(dp * getDestiny(context));
    }

    public static int pxToDp(Context context, int px) {
        return Math.round(px / getDestiny(context));
    }

    public static float getDestiny(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static CellView getValue(List<CellView> values, int position) {
        CellView value = null;
        for (CellView v : values) {
            if (v.getPosition() == position) {
                value = v;
                break;
            }
        }
        return value;
    }

}
