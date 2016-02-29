package com.den13l.new2048;

import java.util.List;

/**
 * Created by erdenierdyneev on 19.02.16.
 */
public interface ShiftEndListener {

    void onShifted(List<CellView> cellsAfterShift);
}
