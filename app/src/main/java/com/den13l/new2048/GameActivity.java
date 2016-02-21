package com.den13l.new2048;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by erdenierdyneev on 13.02.16.
 */
public class GameActivity extends AppCompatActivity {

    public static final String TAG = "DENI";

    private GestureDetectorCompat detector;
    private List<Value> values;
    private Model model;
    private boolean isSwiping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        detector = new GestureDetectorCompat(this, new MyGestureListener());
        model = new Model(Utils.getDeviceWidth(this));
        int cellMargin = model.getCellMargin();
        int cellsCountInRow = model.getCellsCountInRow();

        GridView board = (GridView) findViewById(R.id.cellBoard);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) board.getLayoutParams();
        params.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);
        CellAdapter cellAdapter = new CellAdapter(this, model);
        board.setAdapter(cellAdapter);
        board.setNumColumns(cellsCountInRow);
        board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        GridView board2 = (GridView) findViewById(R.id.valueBoard);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) board2.getLayoutParams();
        params2.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);
        ValueAdapter valueAdapter = new ValueAdapter(this, model);
        board2.setAdapter(valueAdapter);
        board2.setNumColumns(cellsCountInRow);
        board2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        values = valueAdapter.getValues();
        model.initValues(values);

        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void onSwipeLeft() {
        Log.d(TAG, "left swipe");
        SwipeEndListener swipeEndListener = new SwipeEndListener() {
            @Override
            public void onSwiped(List<Value> swipedValues) {
                isSwiping = false;
                values = swipedValues;
                Log.d(TAG, "onSwiped");
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeLeft(values, swipeEndListener);
        }
    }

    private void onSwipeRight() {
        Log.d(TAG, "right swipe");
        SwipeEndListener swipeEndListener = new SwipeEndListener() {
            @Override
            public void onSwiped(List<Value> swipedValues) {
                isSwiping = false;
                values = swipedValues;
                Log.d(TAG, "onSwiped");
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeRight(values, swipeEndListener);
        }
    }

    private void onSwipeTop() {
        Log.d(TAG, "top swipe");
        SwipeEndListener swipeEndListener = new SwipeEndListener() {
            @Override
            public void onSwiped(List<Value> swipedValues) {
                isSwiping = false;
                values = swipedValues;
                Log.d(TAG, "onSwiped");
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeTop(values, swipeEndListener);
        }
    }

    private void onSwipeBottom() {
        Log.d(TAG, "bottom swipe");
        SwipeEndListener swipeEndListener = new SwipeEndListener() {
            @Override
            public void onSwiped(List<Value> swipedValues) {
                isSwiping = false;
                values = swipedValues;
                Log.d(TAG, "onSwiped");
            }
        };
        if (!isSwiping) {
            isSwiping = true;
            model.onSwipeBottom(values, swipeEndListener);
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

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