package com.example.lab3.models.bot;

import com.example.lab3.models.Move;
import com.example.lab3.models.Position;
import com.example.lab3.models.gameboard.BoardCellState;
import com.example.lab3.models.gameboard.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
    private GameBoard gameBoard;
    public Bot(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Move nextMove() {
        List<Move> routineMoves = getRoutineMoves();
        List<Move> attackMoves = getAttackMoves();
        int size = attackMoves.size();
        Random r = new Random();
        if (size > 0) {

            return attackMoves.get(r.nextInt(size));
        }

        size = routineMoves.size();
        if (size > 0) {
            return routineMoves.get(r.nextInt(size));
        }
        return null;
    }
    private List<Move> getRoutineMoves() {
        List<Move> list = new ArrayList<>();
        for (int i = 0; i < gameBoard.getBoardSize(); i++) {
            for (int j = 0; j < gameBoard.getBoardSize(); j++) {
                if (gameBoard.cellAt(j, i) == BoardCellState.OCCUPIED_WHITE) {
                    if (j + 1 < gameBoard.getBoardSize() && i + 1 < gameBoard.getBoardSize() && gameBoard.cellAt(j+1, i+1) == BoardCellState.FREE) {
                        list.add(new Move(new Position(i, j), new Position(i+1,j+1)));
                    } else if (j + 1 <= gameBoard.getBoardSize() && i - 1 >= 0 && gameBoard.cellAt(j+1, i-1) == BoardCellState.FREE) {
                        list.add(new Move(new Position(i, j), new Position(i-1,j+1)));
                    }
                }
            }
        }
        return list;
    }
    private List<Move> getAttackMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < gameBoard.getBoardSize(); i++) {
            for (int j = 0; j < gameBoard.getBoardSize(); j++) {
                if (gameBoard.cellAt(j, i) == BoardCellState.OCCUPIED_WHITE) {
                    if (i + 2 < gameBoard.getBoardSize() && j + 2 < gameBoard.getBoardSize() &&
                            gameBoard.cellAt(j+1, i+1) == BoardCellState.OCCUPIED_BLACK &&
                            gameBoard.cellAt(j+2, i+2) == BoardCellState.FREE) {
                        moves.add(new Move(new Position(i, j), new Position(i+2, j+2)));
                    } else if (i - 2 > 0 && j + 2 < gameBoard.getBoardSize() &&
                            gameBoard.cellAt(j+1, i-1) == BoardCellState.OCCUPIED_BLACK &&
                            gameBoard.cellAt(j+2, i-2) == BoardCellState.FREE) {
                        moves.add(new Move(new Position(i, j), new Position(i-2, j+2)));
                    }
                }
            }
        }
        return moves;
    }
}
