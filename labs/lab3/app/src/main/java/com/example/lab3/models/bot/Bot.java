package com.example.lab3.models.bot;

import com.example.lab3.models.Move;
import com.example.lab3.models.Position;
import com.example.lab3.models.gameboard.BoardCellState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
    private int boardSize;
    private BoardCellState cells[][];
    public Bot(int boardSize, BoardCellState cells[][]) {
        this.cells = cells;
        this.boardSize = boardSize;
    }

    public Move nextMove() {
        List<Move> routineMoves = getRoutineMoves();
        List<Move> attackMoves = getAttackMoves();
        /*for (Move m : routineMoves) {
            System.out.println(m.getFrom().y + " " + m.getFrom().x + " -> " +
                    m.getTo().y + " " + m.getTo().x);
        }
        if (attackMoves.size() == 0) {
            System.out.println("No attacking moves for bot! :C");
        } else {
            System.out.println("Attacking moves are the following:");
            for (Move m : attackMoves) {
                System.out.println(m.getFrom().y + " " + m.getFrom().x + " -> " +
                        m.getTo().y + " " + m.getTo().x);
            }
        }*/
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
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (cells[j][i] == BoardCellState.OCCUPIED_WHITE) {
                    if (j + 1 < boardSize && i + 1 < boardSize && cells[j+1][i+1] == BoardCellState.FREE) {
                        list.add(new Move(new Position(i, j), new Position(i+1,j+1), false, false));
                    } else if (j + 1 <= boardSize && i - 1 >= 0 && cells[j+1][i-1] == BoardCellState.FREE) {
                        list.add(new Move(new Position(i, j), new Position(i-1,j+1), false, true));
                    }
                }
            }
        }
        return list;
    }
    private List<Move> getAttackMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (cells[j][i] == BoardCellState.OCCUPIED_WHITE) {
                    if (i + 2 < boardSize && j + 2 < boardSize &&
                            cells[j+1][i+1] == BoardCellState.OCCUPIED_BLACK &&
                            cells[j+2][i+2] == BoardCellState.FREE) {
                        moves.add(new Move(new Position(i, j), new Position(i+2, j+2), true, false));
                    } else if (i - 2 > 0 && j + 2 < boardSize &&
                            cells[j+1][i-1] == BoardCellState.OCCUPIED_BLACK &&
                            cells[j+2][i-2] == BoardCellState.FREE) {
                        moves.add(new Move(new Position(i, j), new Position(i-2, j+2), true, true));
                    }
                }
            }
        }
        return moves;
    }
}
