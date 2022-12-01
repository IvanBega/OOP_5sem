package com.example.lab3;

import android.view.MotionEvent;

import com.example.lab3.models.gameboard.GameBoardController;

public class OnTouchTask implements Runnable {
    private final MotionEvent event;
    private final GameBoardController gameBoard;

    OnTouchTask(MotionEvent event, GameBoardController gameBoard) {
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