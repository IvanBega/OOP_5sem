package com.example.lab3;
import static java.util.concurrent.Executors.newFixedThreadPool;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.lab3.models.gameboard.GameBoardController;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GameView extends View {
    private final ExecutorService executor = newFixedThreadPool(2);
    private final int BOARD_SIZE = 8;
    private GameBoardController boardController;
    public GameView(Context context) {
        super(context);
        assignTouchListener();
        boardController = new GameBoardController(BOARD_SIZE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boardController.drawCells(canvas);

    }
    private void assignTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Future touchFuture = executor.submit(new OnTouchTask(event, boardController));
                try {
                    touchFuture.get();
                } catch (Exception e) {
                    Log.e("Touch exception", Arrays.toString(e.getStackTrace()));
                    Thread.currentThread().interrupt();
                }
                invalidate();
                return true;
            }
        });
    }
}
