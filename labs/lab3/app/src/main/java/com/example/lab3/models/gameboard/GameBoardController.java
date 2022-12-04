package com.example.lab3.models.gameboard;

import android.graphics.Canvas;
import android.view.View;
import android.widget.EditText;

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
        Position pos = getCellPositionByCoords((int)x, (int)y);
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
        if (gameBoard.canPerformMove(from, to)) {
            gameBoard.performMove(new Move(from, to), MoveType.PLAYER);
            makeBotMove();
        }
    }

    private void makeBotMove() {
        Move move = bot.nextMove();
        if (move == null) {
            gameBoard.setWhiteCheckerCount(0);
            return;
        }
        gameBoard.performMove(move, MoveType.BOT);
    }

    public void drawCells(Canvas canvas) {
        gameBoard.drawCells(canvas);
    }
}
