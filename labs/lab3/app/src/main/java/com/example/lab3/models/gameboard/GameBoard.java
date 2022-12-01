package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
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
        BoardCellState checkerType = cells[move.getFrom().y][move.getFrom().x];
        boolean isAttacking = isMoveAttacking(move.getFrom(), move.getTo());
        System.out.println(isAttacking);
        if (move.getTo().y == 0 && isBlack(checkerType)) {
            checkerType = BoardCellState.OCCUPIED_BLACK_QUEEN;
        }
        if (move.getTo().y == boardSize - 1 && isWhite(checkerType)) {
            checkerType = BoardCellState.OCCUPIED_WHITE_QUEEN;
        }
        if (isAttacking) {
            cells[move.getFrom().y][move.getFrom().x] = BoardCellState.FREE;
            cells[(move.getFrom().y + move.getTo().y) / 2][(move.getFrom().x + move.getTo().x) / 2] = BoardCellState.FREE;
            cells[move.getTo().y][move.getTo().x] = checkerType;
        } else {
            cells[move.getFrom().y][move.getFrom().x] = BoardCellState.FREE;
            cells[move.getTo().y][move.getTo().x] = checkerType;
        }
    }
    public boolean isMoveAttacking(Position from, Position to) {
        return canAttackForward(from, to) ||
                canAttackBackward(from, to);
    }
    public boolean canGoForward(Position from, Position to) {
        if (cells[from.y][from.x] == BoardCellState.FREE ||
            isWhite(cells[from.y][from.x])) return false;
        if (cells[to.y][to.x] != BoardCellState.FREE) return false;
        if (from.y - 1 != to.y) return false;
        if (from.x + 1 != to.x && from.x - 1 != to.x) return false;
        return true;
    }
    public boolean canGoBackward(Position from, Position to) {
        if (cells[from.y][from.x] != BoardCellState.OCCUPIED_BLACK_QUEEN) return false;
        if (cells[to.y][to.x] != BoardCellState.FREE) return false;
        if (from.y + 1 != to.y) return false;
        if (from.x - 1 != to.x && from.x + 1 != to.x) return false;
        return true;
    }
    public boolean canAttackForward(Position from, Position to) {
        MoveType type = MoveType.PLAYER;
        if (isWhite(cells[from.y][from.x])) {
            type = MoveType.BOT;
        }
        if (from.y - 2 != to.y) return false;
        if (from.x + 2 != to.x && from.x - 2 != to.x) return false;
        if (type == MoveType.PLAYER) {
            if (from.x + 2 == to.x && isWhite(cells[from.y - 1][from.x + 1])) return true;
            if (from.x - 2 == to.x && isWhite(cells[from.y - 1][from.x - 1])) return true;
        } else {
            if (cells[from.y][from.x] != BoardCellState.OCCUPIED_WHITE_QUEEN) return false;
            if (from.x + 2 == to.x && isBlack(cells[from.y - 1][from.x + 1])) return true;
            if (from.x - 2 == to.x && isBlack(cells[from.y - 1][from.x - 1])) return true;
        }

        return false;

    }
    public boolean canAttackBackward(Position from, Position to) {
        if (cells[from.y][from.x] == BoardCellState.OCCUPIED_BLACK) return false;
        if (cells[from.y][from.x] == BoardCellState.FREE) return false;
        if (from.y + 2 != to.y) return false;
        if (from.x - 2 != to.x && from.x + 2 != to.x) return false;
        if (cells[from.y][from.x] == BoardCellState.OCCUPIED_BLACK_QUEEN) {
            if (from.x - 2 == to.x && isWhite(cells[from.y + 1][from.x - 1])) return true;
            if (from.x + 2 == to.x && isWhite(cells[from.y + 1][from.x + 1])) return true;
        } else {
            // bot
            if (from.x - 2 == to.x && isBlack(cells[from.y + 1][from.x - 1])) return true;
            if (from.x + 2 == to.x && isBlack(cells[from.y + 1][from.x + 1])) return true;
        }

        return false;
    }
    public boolean canPerformMove(Position from, Position to) {
        return canAttackForward(from, to) || canGoForward(from, to) ||
                canGoBackward(from, to) || canAttackBackward(from, to);
    }
    private boolean isBlack(BoardCellState state) {
        return state == BoardCellState.OCCUPIED_BLACK || state == BoardCellState.OCCUPIED_BLACK_QUEEN;
    }
    private boolean isWhite(BoardCellState state) {
        return state == BoardCellState.OCCUPIED_WHITE || state == BoardCellState.OCCUPIED_WHITE_QUEEN;
    }
}
