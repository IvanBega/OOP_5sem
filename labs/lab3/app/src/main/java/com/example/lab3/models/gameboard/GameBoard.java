package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBoard {
    private BoardCellState[][] cells;
    private Paint whitePaint = new Paint();
    private Paint blackPaint = new Paint();
    private Paint whiteChPaint = new Paint();
    private Paint blackChPaint = new Paint();
    private int boardSize;
    private int cellSizePx;
    public GameBoard(int boardSize) {
        this.boardSize = boardSize;
        whitePaint.setARGB(0, 0, 0, 0);
        blackPaint.setARGB(255, 0, 0, 0);
        whiteChPaint.setARGB(200, 200, 200, 200);
        blackChPaint.setARGB(200, 128, 128, 128);
        cells = new BoardCellState[boardSize][boardSize];
        initCells();
    }
    public BoardCellState cellAt(int x, int y) {
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) return null;

        return cells[x][y];
    }
    public int getBoardSize() {
        return boardSize;
    }
    public void setCell(int x, int y, BoardCellState state) {
        cells[x][y] = state;
    }
    public void drawCells(Canvas canvas) {
        cellSizePx = canvas.getWidth() / boardSize;
        drawGrid(canvas);
        drawCheckers(canvas);
    }
    public void initCells() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = BoardCellState.FREE;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 1 /*&& (i!=1)*/) {
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
    public int getCellSizePx() {
        return cellSizePx;
    }
}
