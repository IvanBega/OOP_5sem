package com.example.lab3.models;

public class Move {
    private Position from;
    private Position to;
    private boolean isAttacking;
    private boolean leftDirection;
    public Move(Position from, Position to, boolean isAttacking, boolean leftDirection) {
        this.from = from;
        this.to = to;
        this.leftDirection = leftDirection;
        this.isAttacking = isAttacking;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isLeftDirection() {
        return leftDirection;
    }
}
