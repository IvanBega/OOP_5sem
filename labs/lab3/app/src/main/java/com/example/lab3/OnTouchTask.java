package com.example.lab3;

import android.view.MotionEvent;

import com.example.lab3.models.gameboard.GameBoard;

public class OnTouchTask implements Runnable {
    private final MotionEvent event;
    private final GameBoard gameBoard;

    OnTouchTask(MotionEvent event, GameBoard gameBoard) {
        this.event = event;
        this.gameBoard = gameBoard;
    }


    @Override
    public void run() {
        if (event == null)
            return;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            gameBoard.playerClick(event.getX(), event.getY());
        }
    }
}