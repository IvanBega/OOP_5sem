package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab3.models.Move;
import com.example.lab3.models.MoveType;
import com.example.lab3.models.Position;
import com.example.lab3.models.bot.Bot;

public class GameBoardController {
    private Bot bot;
    private GameBoard gameBoard;
    private Position selectedCellPos = null;
    private MoveType move = MoveType.PLAYER;
    public GameBoardController(int boardSize) {
        gameBoard = new GameBoard(boardSize);
        gameBoard.initCells();
        bot = new Bot(gameBoard);
    }



    private Position getCellPositionByCoords(int x, int y) {
        x-=10;
        y-=20;
        return new Position(x/ gameBoard.getCellSizePx(),y/ gameBoard.getCellSizePx());
    }
    public void playerClick(float x, float y) {
        if (move == MoveType.BOT) {
            return;
        }
        //bot.nextMove();
        //System.out.println(x + " " + y);
        Position pos = getCellPositionByCoords((int)x, (int)y);
        System.out.println(pos.y + " " + pos.x);
        if (selectedCellPos != null) {
            if (gameBoard.cellAt(pos.y,pos.x) == BoardCellState.OCCUPIED_BLACK) {
                selectedCellPos = new Position(pos.x, pos.y);
                return;
            }
            analyzeMove(selectedCellPos, pos);
            selectedCellPos = null;
        } else {
                selectedCellPos = new Position(pos.x, pos.y);
        }
    }
    private void analyzeMove(Position from, Position to) {
        if (canMoveCheckerToFreePosition(from, to)) {
            gameBoard.setCell(from.y,from.x, BoardCellState.FREE);
            gameBoard.setCell(to.y, to.x, BoardCellState.OCCUPIED_BLACK);
            makeBotMove();
            return;
        }
        if (canMoveCheckerOverWhite(from, to)) {
            // 4 3 -> 2 1, 6 1
                //    3 2, 5 2
            gameBoard.setCell((from.y + to.y) / 2, (from.x + to.x) / 2,  BoardCellState.FREE);
            gameBoard.setCell(from.y, from.x, BoardCellState.FREE);
            gameBoard.setCell(to.y,to.x, BoardCellState.OCCUPIED_BLACK);
            makeBotMove();
        }
    }

    private void makeBotMove() {
        Move move = bot.nextMove();
        if (move.isAttacking()) {
            gameBoard.setCell(move.getFrom().y, move.getFrom().x, BoardCellState.FREE);
            gameBoard.setCell((move.getFrom().y + move.getTo().y)/2,(move.getFrom().x+move.getTo().x)/2, BoardCellState.FREE);
            gameBoard.setCell(move.getTo().y,move.getTo().x, BoardCellState.OCCUPIED_WHITE);
        } else {
            gameBoard.setCell(move.getFrom().y, move.getFrom().x, BoardCellState.FREE);
            gameBoard.setCell(move.getTo().y, move.getTo().x, BoardCellState.OCCUPIED_WHITE);
        }
    }
    private boolean canMoveCheckerToFreePosition(Position from, Position to) {
        if (gameBoard.cellAt(to.y, to.x) != BoardCellState.FREE) return false;
        if (from.y - 1 != to.y) return false;
        if (from.x + 1 != to.x && from.x - 1 != to.x) return false;
        return true;
    }
    private boolean canMoveCheckerOverWhite(Position from, Position to) {
        if (from.y - 2 != to.y) return false;
        if (from.x + 2 != to.x && from.x - 2 != to.x) return false;
        if (from.x + 2 == to.x && gameBoard.cellAt(from.y - 1,from.x + 1) == BoardCellState.OCCUPIED_WHITE) return true;
        if (from.x - 2 == to.x && gameBoard.cellAt(from.y - 1, from.x - 1) == BoardCellState.OCCUPIED_WHITE) return true;
        return false;

    }
    private boolean canAttackSecondTime(Position pos) {
        return false;
    }
    public void drawCells(Canvas canvas) {
        gameBoard.drawCells(canvas);
    }
}
