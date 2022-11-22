package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab3.models.cell.Cell;

public class GameBoard {
    private int boardSize;
    private int cellSizePx;
    private Paint whitePaint = new Paint();
    private Paint blackPaint = new Paint();
    private Paint whiteChPaint = new Paint();
    private Paint blackChPaint = new Paint();
    private BoardCellState[][] cells;
    public GameBoard(int boardSize) {
        this.boardSize = boardSize;
        whitePaint.setARGB(0, 0, 0, 0);
        blackPaint.setARGB(255, 0, 0, 0);
        whiteChPaint.setARGB(128, 128, 128, 128);
        blackChPaint.setARGB(200, 128, 128, 128);
        cells = new BoardCellState[boardSize][boardSize];
        initCells();
    }
    public void drawCells(Canvas canvas) {
        cellSizePx = canvas.getWidth() / boardSize;
        drawGrid(canvas);
        drawCheckers(canvas);
    }
    public void eraseCells() {
        /*System.out.println("From eraseCells()");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                switch (cells[j][i]) {
                    case OCCUPIED_WHITE:
                        canvas.drawCircle(i*cellSizePx + cellSizePx / 2,
                                j*cellSizePx + cellSizePx/2, cellSizePx/2,blackPaint);
                        break;
                    case OCCUPIED_BLACK:
                        canvas.drawCircle(i*cellSizePx + cellSizePx / 2,
                                j*cellSizePx + cellSizePx/2, cellSizePx/2, whitePaint);
                }
            }
        }*/
    }
    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    canvas.drawRect(i * cellSizePx, j * cellSizePx,
                            i * cellSizePx + cellSizePx, j * cellSizePx + cellSizePx, whitePaint);
                }
                else {
                    canvas.drawRect(i * cellSizePx, j * cellSizePx,
                            i * cellSizePx + cellSizePx, j * cellSizePx + cellSizePx, blackPaint);
                }
            }
        }
    }
    private void initCells() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = BoardCellState.FREE;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    cells[i][j] = BoardCellState.OCCUPIED_WHITE;
                }
            }
        }
        for (int i = 5; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 1) {
                    cells[i][j] = BoardCellState.OCCUPIED_BLACK;
                }
            }
        }
    }
    private void drawCheckers(Canvas canvas) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                switch (cells[j][i]) {
                    case OCCUPIED_WHITE:
                        canvas.drawCircle(i*cellSizePx + cellSizePx / 2,
                                j*cellSizePx + cellSizePx/2, cellSizePx/2,whiteChPaint);
                        break;
                    case OCCUPIED_BLACK:
                        canvas.drawCircle(i*cellSizePx + cellSizePx / 2,
                                j*cellSizePx + cellSizePx/2, cellSizePx/2,blackChPaint);
                }
            }
        }
    }

    public void playerClick(float x, float y) {
        System.out.println(x + " " + y);
    }
}
