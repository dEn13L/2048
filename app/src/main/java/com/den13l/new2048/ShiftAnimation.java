package com.den13l.new2048;

import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 23.02.16.
 */
public class ShiftAnimation implements Animation.AnimationListener {

    private List<LineShift> lineShifts;
    private List<CellView> cells;
    private ShiftEndListener shiftEndListener;
    private int activeShiftsCount;
    private int animatedShifts;

    public ShiftAnimation(List<CellView> cells) {
        this.lineShifts = new ArrayList<>();
        this.cells = cells;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (LineShift lineShift : lineShifts) {
            stringBuilder.append(lineShift.toString());
        }
        return stringBuilder.toString();
    }

    public void addLineShift(LineShift lineShift) {
        lineShifts.add(lineShift);
    }

    public void setShiftEndListener(ShiftEndListener shiftEndListener) {
        this.shiftEndListener = shiftEndListener;
    }

    public void start() {
        activeShiftsCount = getActiveShiftsCount();
        for (LineShift lineShift : lineShifts) {
            List<Shift> shifts = lineShift.getShiftList();
            for (Shift shift : shifts) {
                CellView sourceCell = shift.getSourceCell();
                boolean hasShiftToCell = hasShiftToCell(sourceCell);
                ShiftListener shiftListener = new ShiftListener(this, !hasShiftToCell);
                shift.start(shiftListener);
            }
        }
        if (activeShiftsCount == 0 && shiftEndListener != null) {
            shiftEndListener.onShifted(cells);
        }
    }

    private int getActiveShiftsCount() {
        int shiftsCount = 0;
        for (LineShift lineShift : lineShifts) {
            List<Shift> shifts = lineShift.getShiftList();
            shiftsCount += shifts.size();
        }
        return shiftsCount;
    }

    public boolean hasShiftToCell(CellView cell) {
        for (LineShift lineShift : lineShifts) {
            List<Shift> shifts = lineShift.getShiftList();
            for (Shift shift : shifts) {
                CellView destCell = shift.getDestCell();
                if (destCell.equals(cell)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animatedShifts++;
        if (animatedShifts == activeShiftsCount && shiftEndListener != null) {
            shiftEndListener.onShifted(cells);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}