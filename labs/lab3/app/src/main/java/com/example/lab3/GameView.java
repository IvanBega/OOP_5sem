package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.example.lab3.models.gameboard.GameBoard;

public class GameView extends View {
    private final int BOARD_SIZE = 8;
    private GameBoard board = new GameBoard(BOARD_SIZE);
    public GameView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        board.drawCells(canvas);
    }
}
