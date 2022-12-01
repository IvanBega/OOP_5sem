package com.example.lab3.models.gameboard;

import android.graphics.Canvas;

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
        /*boolean isAttacking = gameBoard.isMoveAttacking(from, to);
        if (gameBoard.canGoForward(from, to)) {
            gameBoard.performMove(new Move(from, to), MoveType.PLAYER);
            makeBotMove();
            return;
        }
        if (gameBoard.canAttackForward(from, to)) {
            gameBoard.performMove(new Move(from, to), MoveType.PLAYER);
            makeBotMove();
        }*/
        if (gameBoard.canPerformMove(from, to)) {
            gameBoard.performMove(new Move(from, to), MoveType.PLAYER);
            makeBotMove();
        }
    }

    private void makeBotMove() {
        Move move = bot.nextMove();
        gameBoard.performMove(move, MoveType.BOT);
    }
    /*private boolean canMoveCheckerToFreePosition(Position from, Position to) {
        if (gameBoard.cellAt(to.y, to.x) != BoardCellState.FREE) return false;
        if (from.y - 1 != to.y) return false;
        if (from.x + 1 != to.x && from.x - 1 != to.x) return false;
        return true;
    }*/
    /*private boolean canMoveCheckerOverWhite(Position from, Position to) {
        if (from.y - 2 != to.y) return false;
        if (from.x + 2 != to.x && from.x - 2 != to.x) return false;
        if (from.x + 2 == to.x && gameBoard.cellAt(from.y - 1,from.x + 1) == BoardCellState.OCCUPIED_WHITE) return true;
        if (from.x - 2 == to.x && gameBoard.cellAt(from.y - 1, from.x - 1) == BoardCellState.OCCUPIED_WHITE) return true;
        return false;

    }*/
    private boolean canAttackSecondTime(Position pos) {
        return false;
    }
    public void drawCells(Canvas canvas) {
        gameBoard.drawCells(canvas);
    }
}
