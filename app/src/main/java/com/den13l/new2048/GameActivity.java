package com.den13l.new2048;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class GameActivity extends AppCompatActivity {

    public static final String TAG = "DENI";

    private GestureDetectorCompat detector;
    private List<Cell> cells;
    private Model model;
    private boolean isSwiping;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        detector = new GestureDetectorCompat(this, new GestureListener());
        model = new Model(this);

        score = (TextView) findViewById(R.id.score);

        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        RelativeLayout boardBackground = (RelativeLayout) findViewById(R.id.boardBackground);

        int rootPadding = model.getRootPadding(this);
        root.setPadding(rootPadding, rootPadding, rootPadding, rootPadding);

        int boardPadding = model.getBoardPadding();
        boardBackground.setPadding(boardPadding, boardPadding, boardPadding, boardPadding);

        GridView backgroundBoard = (GridView) findViewById(R.id.backgroundBoard);
        GridView foregroundBoard = (GridView) findViewById(R.id.foregroundBoard);

        initBoard(backgroundBoard);
        initBoard(foregroundBoard);

        BoardAdapter boardAdapter = (BoardAdapter) foregroundBoard.getAdapter();
        cells = boardAdapter.getCells();
        model.initCells(cells);

        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    private void initBoard(GridView board) {
        int cellsCountInRow = model.getCellsCountInLine();
        int gridSpacing = model.getGridSpacing(this);

        BoardAdapter boardAdapter = new BoardAdapter(this, model);
        board.setAdapter(boardAdapter);
        board.setNumColumns(cellsCountInRow);
        board.setHorizontalSpacing(gridSpacing);
        board.setVerticalSpacing(gridSpacing);
        board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void onSwipeLeft() {
        Log.d(TAG, "left swipe. " + cells.toString());
        ShiftEndListener shiftEndListener = new ShiftEndListener() {
            @Override
            public void onShifted(int destNumbersSum, boolean isShifted) {
                isSwiping = false;
                if (isShifted) {
                    model.initCells(cells, 1);
                }
                String scoreText = (String) score.getText();
                int scoreNumber = Integer.valueOf(scoreText) + destNumbersSum;
                score.setText(String.valueOf(scoreNumber));
                Log.d(TAG, "onShifted. " + cells.toString());
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeLeft(cells, shiftEndListener);
        }
    }

    private void onSwipeTop() {
        Log.d(TAG, "top swipe. " + cells.toString());
        ShiftEndListener shiftEndListener = new ShiftEndListener() {
            @Override
            public void onShifted(int destNumbersSum, boolean isShifted) {
                isSwiping = false;
                if (isShifted) {
                    model.initCells(cells, 1);
                }
                String scoreText = (String) score.getText();
                int scoreNumber = Integer.valueOf(scoreText) + destNumbersSum;
                score.setText(String.valueOf(scoreNumber));
                Log.d(TAG, "onShifted. " + cells.toString());
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeTop(cells, shiftEndListener);
        }
    }

    private void onSwipeRight() {
        Log.d(TAG, "right swipe. " + cells.toString());
        ShiftEndListener shiftEndListener = new ShiftEndListener() {
            @Override
            public void onShifted(int destNumbersSum, boolean isShifted) {
                isSwiping = false;
                if (isShifted) {
                    model.initCells(cells, 1);
                }
                String scoreText = (String) score.getText();
                int scoreNumber = Integer.valueOf(scoreText) + destNumbersSum;
                score.setText(String.valueOf(scoreNumber));
                Log.d(TAG, "onShifted. " + cells.toString());
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeRight(cells, shiftEndListener);
        }
    }

    private void onSwipeBottom() {
        Log.d(TAG, "bottom swipe. " + cells.toString());
        ShiftEndListener shiftEndListener = new ShiftEndListener() {
            @Override
            public void onShifted(int destNumbersSum, boolean isShifted) {
                isSwiping = false;
                if (isShifted) {
                    model.initCells(cells, 1);
                }
                String scoreText = (String) score.getText();
                int scoreNumber = Integer.valueOf(scoreText) + destNumbersSum;
                score.setText(String.valueOf(scoreNumber));
                Log.d(TAG, "onShifted. " + cells.toString());
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeBottom(cells, shiftEndListener);
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
            }
            return true;
        }
    }
}