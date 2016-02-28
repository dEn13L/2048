package com.den13l.new2048;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erdenierdyneev on 28.02.16.
 */
public class LineShift {

    private List<CellView> cellList;
    private List<Shift> shiftList;
    private int cellsCountInLine;
    private int cellWidth;

    public LineShift(List<CellView> cellList, int cellsCountInLine, int cellWidth) {
        this.cellList = cellList;
        this.shiftList = new ArrayList<>();
        this.cellsCountInLine = cellsCountInLine;
        this.cellWidth = cellWidth;
        createShifts();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Shift shift : shiftList) {
            stringBuilder.append(shift.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    private void createShifts() {
        for (int i = 0; i < cellList.size(); i++) {
            CellView sourceCell = cellList.get(i);
            if (sourceCell.hasNumber()) {
                CellView destCell = getDestCell(sourceCell, i);
                if (destCell != null) {
                    Shift shift = new Shift(sourceCell, destCell, cellsCountInLine, cellWidth);
                    mergeShifts(shift);
                    shiftList.add(shift);
                }
            }
        }
    }

    private CellView getDestCell(CellView sourceCell, int i) {
        CellView destCell = null;
        for (int j = i; j >= 0; j--) {
            CellView destCellCandidate = cellList.get(j);
            // last suitable candidate will be destination cell
            if (canShift(sourceCell, destCellCandidate)) {
                destCell = destCellCandidate;
            } else {
                break;
            }
        }
        return destCell;
    }

    private boolean canShift(CellView sourceCell, CellView destCell) {
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
                int doubleDestNumber = 2 * mergeShift.getDestNumber();
                shift.setDestNumber(doubleDestNumber);
                mergeShift.setDestNumber(doubleDestNumber);
                break;
            }
        }
    }
}
