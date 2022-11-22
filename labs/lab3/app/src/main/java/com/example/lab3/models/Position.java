package com.example.lab3.models;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position toPosition(float x, float y, int fieldSize, int cellSize) {
        Position res = new Position((int) (x / cellSize), (int) (y / cellSize));
        if (res.x >= fieldSize || res.y >= fieldSize)
            return null;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position))
            return false;

        Position pos = (Position) (o);
        return pos.x == x && pos.y == y;
    }

}
