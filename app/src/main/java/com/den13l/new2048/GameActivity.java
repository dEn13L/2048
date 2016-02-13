package com.den13l.new2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class GameActivity extends AppCompatActivity {

    public static final String TAG = "DENI";
    
    private TableLayout tableLayout;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.tableLayout = (TableLayout) findViewById(R.id.board);

        int widthPx = Utils.getDeviceWidth(this);
        this.model = new Model(this, widthPx);

        int cellPx = model.getCellPx();
        int cellMarginPx = model.getCellMarginPx();
        int innerCellPx = model.getInnerCellPx();
        int cellsCountInRow = model.getCellsCountInRow();

        Log.i(TAG, "widthPx      = " + widthPx);
        Log.i(TAG, "cellPx       = " + cellPx);
        Log.i(TAG, "cellMarginPx = " + cellMarginPx);
        Log.i(TAG, "innerCellPx  = " + innerCellPx);

        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < model.getCellsCountInRow(); i++) {
            TableRow row = new TableRow(this);
            tableLayout.addView(row);
            for (int j = 0; j < model.getCellsCountInRow(); j++) {
                Cell cell = new Cell(this, i, j, cellMarginPx, cellsCountInRow);
                cell.setLayoutParams(new TableRow.LayoutParams(innerCellPx, innerCellPx));
                cells.add(cell);

                row.addView(cell);
            }
        }
        model.initCells(cells);
    }

}
