package com.den13l.new2048;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public static final int CELLS_COUNT_IN_ROW = 4;
    public static final int INITIATED_CELLS = 2;
    public static final int INITIAL_SCALE = 2;

    @SmallTest
    public void test() {
        Model model = new Model(getContext(), 1080);
        int cellMarginPx = model.getCellPadding();
        int innerCellPx = model.getInnerCellWidth();

        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < CELLS_COUNT_IN_ROW; i++) {
            for (int j = 0; j < CELLS_COUNT_IN_ROW; j++) {
                Cell cell = new Cell(getContext(), i, j, cellMarginPx, CELLS_COUNT_IN_ROW);
                cell.setLayoutParams(new TableRow.LayoutParams(innerCellPx, innerCellPx));
                cells.add(cell);
            }
        }
        model.initValues(cells);
    }
}