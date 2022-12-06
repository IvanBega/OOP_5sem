package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lab3.models.Move;
import com.example.lab3.models.MoveType;
import com.example.lab3.models.Position;

public class GameBoard {
    // black - player, white - bot
    private BoardCellState[][] cells;
    private Paint whitePaint = new Paint();
    private Paint blackPaint = new Paint();
    private Paint whiteChPaint = new Paint();
    private Paint blackChPaint = new Paint();
    private Paint textPaint = new Paint();
    private int boardSize;
    private int cellSizePx;
    private int whiteCheckers;
    private int blackCheckers;

    public GameBoard(int boardSize) {
        this.boardSize = boardSize;
        whiteCheckers = 3 * (boardSize / 2);
        blackCheckers = 3 * (boardSize / 2);

        initPaint();
        cells = new BoardCellState[boardSize][boardSize];
        initCells();
    }
    private void initPaint() {
        whitePaint.setColor(Color.WHITE);
        blackPaint.setColor(Color.BLACK);
        whiteChPaint.setColor(Color.LTGRAY);
        blackChPaint.setColor(Color.LTGRAY);
        blackChPaint.setAlpha(160);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
    }
    public BoardCellState cellAt(int x, int y) {
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) return null;

        return cells[x][y];
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void drawCells(Canvas canvas) {
        if (whiteCheckers <= 0) {
            canvas.drawText("BLACK WON", 100, canvas.getWidth() / 2, textPaint);
            return;
        } else if (blackCheckers <= 0){
            canvas.drawText("WHITE WON", 100, canvas.getWidth() / 2, textPaint);
            return;
        }
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
                } else {
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
                        canvas.drawCircle(i * cellSizePx + cellSizePx / 2,
                                j * cellSizePx + cellSizePx / 2, cellSizePx / 2, whiteChPaint);
                        break;
                    case OCCUPIED_BLACK:
                        canvas.drawCircle(i * cellSizePx + cellSizePx / 2,
                                j * cellSizePx + cellSizePx / 2, cellSizePx / 2, blackChPaint);
                        break;
                    case OCCUPIED_WHITE_QUEEN:
                        canvas.drawCircle(i * cellSizePx + cellSizePx / 2,
                                j * cellSizePx + cellSizePx / 2, 1.2f * cellSizePx / 2, whiteChPaint);
                        break;
                    case OCCUPIED_BLACK_QUEEN:
                        canvas.drawCircle(i * cellSizePx + cellSizePx / 2,
                                j * cellSizePx + cellSizePx / 2, 1.2f * cellSizePx / 2, blackChPaint);
                        break;
                }
            }
        }
    }
    public int getCellSizePx() {
        return cellSizePx;
    }
    public void performMove(Move move, MoveType type) {
        BoardCellState checkerType = cells[move.getFrom().getY()][move.getFrom().getX()];
        boolean isAttacking = isMoveAttacking(move.getFrom(), move.getTo());
        if (move.getTo().getY() == 0 && isBlack(checkerType)) {
            checkerType = BoardCellState.OCCUPIED_BLACK_QUEEN;
        }
        if (move.getTo().getY() == boardSize - 1 && isWhite(checkerType)) {
            checkerType = BoardCellState.OCCUPIED_WHITE_QUEEN;
        }
        if (isAttacking) {
            cells[move.getFrom().getY()][move.getFrom().getX()] = BoardCellState.FREE;
            cells[(move.getFrom().getY() + move.getTo().getY()) / 2][(move.getFrom().getX() + move.getTo().getX()) / 2] = BoardCellState.FREE;
            cells[move.getTo().getY()][move.getTo().getX()] = checkerType;

            if (isWhite(checkerType)) {
                blackCheckers--;
            } else {
                whiteCheckers--;
            }
        } else {
            cells[move.getFrom().getY()][move.getFrom().getX()] = BoardCellState.FREE;
            cells[move.getTo().getY()][move.getTo().getX()] = checkerType;
        }

        System.out.println("White = " + whiteCheckers + " | Black = " + blackCheckers);
    }
    public boolean isMoveAttacking(Position from, Position to) {
        return canAttackForward(from, to) ||
                canAttackBackward(from, to);
    }
    public boolean canGoForward(Position from, Position to) {
        if (cells[from.getY()][from.getX()] == BoardCellState.FREE ||
            isWhite(cells[from.getY()][from.getX()])) return false;
        if (cells[to.getY()][to.getX()] != BoardCellState.FREE) return false;
        if (from.getY() - 1 != to.getY()) return false;
        if (from.getX() + 1 != to.getX() && from.getX() - 1 != to.getX()) return false;
        return true;
    }
    public boolean canGoBackward(Position from, Position to) {
        if (cells[from.getY()][from.getX()] != BoardCellState.OCCUPIED_BLACK_QUEEN) return false;
        if (cells[to.getY()][to.getX()] != BoardCellState.FREE) return false;
        if (from.getY() + 1 != to.getY()) return false;
        if (from.getX() - 1 != to.getX() && from.getX() + 1 != to.getX()) return false;
        return true;
    }
    public boolean canAttackForward(Position from, Position to) {
        MoveType type = MoveType.PLAYER;
        if (isWhite(cells[from.getY()][from.getX()])) {
            type = MoveType.BOT;
        }
        if (from.getY() - 2 != to.getY()) return false;
        if (from.getX() + 2 != to.getX() && from.getX() - 2 != to.getX()) return false;
        if (type == MoveType.PLAYER) {
            if (from.getX() + 2 == to.getX() && isWhite(cells[from.getY() - 1][from.getX() + 1])) return true;
            if (from.getX() - 2 == to.getX() && isWhite(cells[from.getY() - 1][from.getX() - 1])) return true;
        } else {
            if (cells[from.getY()][from.getX()] != BoardCellState.OCCUPIED_WHITE_QUEEN) return false;
            if (from.getX() + 2 == to.getX() && isBlack(cells[from.getY() - 1][from.getX() + 1])) return true;
            if (from.getX() - 2 == to.getX() && isBlack(cells[from.getY() - 1][from.getX() - 1])) return true;
        }

        return false;

    }
    public boolean canAttackBackward(Position from, Position to) {
        if (cells[from.getY()][from.getX()] == BoardCellState.OCCUPIED_BLACK) return false;
        if (cells[from.getY()][from.getX()] == BoardCellState.FREE) return false;
        if (from.getY() + 2 != to.getY()) return false;
        if (from.getX() - 2 != to.getX() && from.getX() + 2 != to.getX()) return false;
        if (cells[from.getY()][from.getX()] == BoardCellState.OCCUPIED_BLACK_QUEEN) {
            if (from.getX() - 2 == to.getX() && isWhite(cells[from.getY() + 1][from.getX() - 1])) return true;
            if (from.getX() + 2 == to.getX() && isWhite(cells[from.getY() + 1][from.getX() + 1])) return true;
        } else {
            // bot
            if (from.getX() - 2 == to.getX() && isBlack(cells[from.getY() + 1][from.getX() - 1])) return true;
            if (from.getX() + 2 == to.getX() && isBlack(cells[from.getY() + 1][from.getX() + 1])) return true;
        }

        return false;
    }
    public boolean canPerformMove(Position from, Position to) {
        return canAttackForward(from, to) || canGoForward(from, to) ||
                canGoBackward(from, to) || canAttackBackward(from, to);
    }
    public boolean isBlack(BoardCellState state) {
        return state == BoardCellState.OCCUPIED_BLACK || state == BoardCellState.OCCUPIED_BLACK_QUEEN;
    }
    public boolean isWhite(BoardCellState state) {
        return state == BoardCellState.OCCUPIED_WHITE || state == BoardCellState.OCCUPIED_WHITE_QUEEN;
    }
    public void setWhiteCheckerCount(int i) {
        whiteCheckers = 0;
    }
}
