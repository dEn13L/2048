package com.den13l.new2048;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 28.02.16.
 */
public class LineShift {

    private List<Cell> cellList;
    private List<Shift> shiftList;

    public LineShift(List<Cell> cellList) {
        this.cellList = cellList;
        this.shiftList = new ArrayList<>();
        createShifts();
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    private void createShifts() {
        for (int i = 0; i < cellList.size(); i++) {
            Cell sourceCell = cellList.get(i);
            if (sourceCell.hasNumber()) {
                Cell destCell = getDestCell(sourceCell, i);
                if (destCell != null) {
                    Shift shift = new Shift(sourceCell, destCell);
                    mergeShifts(shift);
                    shiftList.add(shift);
                }
            }
        }
    }

    private Cell getDestCell(Cell sourceCell, int i) {
        Cell destCell = null;
        for (int j = i - 1; j >= 0; j--) {
            Cell destCellCandidate = cellList.get(j);
            // last suitable candidate will be destination cell
            if (canShift(sourceCell, destCellCandidate)) {
                destCell = destCellCandidate;
            } else {
                break;
            }
        }
        return destCell;
    }

    private boolean canShift(Cell sourceCell, Cell destCell) {
        boolean isShiftAllowed = true;
        int destCellShiftsCount = 0;
        for (Shift shift : shiftList) {
            if (shift.getDestCell().equals(destCell)) {
                destCellShiftsCount++;
                if (destCellShiftsCount == 2 || shift.getSourceCell().getNumber() != sourceCell.getNumber()) {
                    isShiftAllowed = false;
                    break;
                }
            }
        }
        return isShiftAllowed;
    }

    private void mergeShifts(Shift mergeShift) {
        for (Shift shift : shiftList) {
            if (shift.getDestCell().equals(mergeShift.getDestCell())) {
                shift.setDestNumber(0);
                mergeShift.setDestNumber(2 * mergeShift.getDestNumber());
                break;
            }
        }
    }
}
