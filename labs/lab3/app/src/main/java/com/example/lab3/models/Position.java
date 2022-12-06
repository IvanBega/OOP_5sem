package com.example.lab3.models;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position))
            return false;

        Position pos = (Position) (o);
        return pos.x == x && pos.y == y;
    }

}
