package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab3.models.Move;
import com.example.lab3.models.MoveType;
import com.example.lab3.models.Position;
import com.example.lab3.models.bot.Bot;
import com.example.lab3.models.cell.Cell;

public class GameBoard {
    private Bot bot;
    private int boardSize;
    private int cellSizePx;
    private Paint whitePaint = new Paint();
    private Paint blackPaint = new Paint();
    private Paint whiteChPaint = new Paint();
    private Paint blackChPaint = new Paint();
    private Position selectedCellPos = null;
    private MoveType move = MoveType.PLAYER;
    private BoardCellState[][] cells;
    public GameBoard(int boardSize) {
        this.boardSize = boardSize;
        whitePaint.setARGB(0, 0, 0, 0);
        blackPaint.setARGB(255, 0, 0, 0);
        whiteChPaint.setARGB(200, 200, 200, 200);
        blackChPaint.setARGB(200, 128, 128, 128);
        cells = new BoardCellState[boardSize][boardSize];
        initCells();
        bot = new Bot(boardSize, cells);
    }
    public void drawCells(Canvas canvas) {
        cellSizePx = canvas.getWidth() / boardSize;
        drawGrid(canvas);
        drawCheckers(canvas);
    }
    public void eraseCells() {

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
                if ((i + j) % 2 == 1 && (i!=1)) {
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
    private Position getCellPositionByCoords(int x, int y) {
        x-=10;
        y-=20;
        return new Position(x/cellSizePx,y/cellSizePx);
    }
    public void playerClick(float x, float y) {
        if (move == MoveType.BOT) {
            return;
        }
        bot.nextMove();
        //System.out.println(x + " " + y);
        Position pos = getCellPositionByCoords((int)x, (int)y);
        System.out.println(pos.y + " " + pos.x);
        if (selectedCellPos != null) {
            analyzeMove(selectedCellPos, pos);
            selectedCellPos = null;
        } else {
            selectedCellPos = new Position(pos.x, pos.y);
        }
    }
    private void analyzeMove(Position from, Position to) {
        if (canMoveCheckerToFreePosition(from, to)) {
            cells[from.y][from.x] = BoardCellState.FREE;
            cells[to.y][to.x] = BoardCellState.OCCUPIED_BLACK;
            makeBotMove();
            return;
        }
        if (canMoveCheckerOverWhite(from, to)) {
            // 4 3 -> 2 1, 6 1
                //    3 2, 5 2
            cells[(from.y + to.y) / 2][(from.x + to.x) / 2] = BoardCellState.FREE;
            cells[from.y][from.x] = BoardCellState.FREE;
            cells[to.y][to.x] = BoardCellState.OCCUPIED_BLACK;
            makeBotMove();
        }

    }

    private void makeBotMove() {
        Move move = bot.nextMove();
        if (move.isAttacking()) {
            cells[move.getFrom().y][move.getFrom().x] = BoardCellState.FREE;
            cells[(move.getFrom().y + move.getTo().y)/2][(move.getFrom().x+move.getTo().x)/2] = BoardCellState.FREE;
            cells[move.getTo().y][move.getTo().x] = BoardCellState.OCCUPIED_WHITE;
        } else {
            cells[move.getFrom().y][move.getFrom().x] = BoardCellState.FREE;
            cells[move.getTo().y][move.getTo().x] = BoardCellState.OCCUPIED_WHITE;
        }
    }

    private boolean canMoveCheckerToFreePosition(Position from, Position to) {
        if (cells[to.y][to.x] != BoardCellState.FREE) return false;
        if (from.y - 1 != to.y) return false;
        if (from.x + 1 != to.x && from.x - 1 != to.x) return false;
        return true;
    }
    private boolean canMoveCheckerOverWhite(Position from, Position to) {
        if (from.y - 2 != to.y) return false;
        if (from.x + 2 != to.x && from.x - 2 != to.x) return false;
        if (from.x + 2 == to.x && cells[from.y - 1][from.x + 1] == BoardCellState.OCCUPIED_WHITE) return true;
        if (from.x - 2 == to.x && cells[from.y - 1][from.x - 1] == BoardCellState.OCCUPIED_WHITE) return true;
        return false;

    }
}
