package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

import java.util.List;

public abstract class AbstractBoard implements Board {

    protected int[][] state;
    protected int goal, width, height;
    protected List<Coordinate> turns;

    public AbstractBoard() {
        this(7, 6, 4);
    }

    public AbstractBoard(int width, int height, int goal) {
        this.state = new int[this.width = width][this.height = height];
        this.goal = goal;
    }

    @Override
    public int getPiece(int x, int y) {
        return (x >= 0 && x < width && y >= 0 & y < height) ? state[x][y] : -1;
    }

    @Override
    public int getGoal() {
        return goal;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public List<Coordinate> getTurns() {
        return turns;
    }

    public String toMatrixString() {
        Board board = this;
        StringBuffer b = new StringBuffer();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                b.append(String.format("[%d,%d]  ", x, y));
            }
            b.append("\n");
        }
        return b.toString();
    }

    public String toString() {
        Board board = this;
        StringBuffer b = new StringBuffer("----------------\nboard = ");
        for (int x = 0; x < board.getWidth(); x++) {
            b.append(x).append(" ");
        }
        b.append("\n");

        for (int y = 0; y < board.getHeight(); y++) {
            b.append("     ").append(y).append(": ");
            for (int x = 0; x < board.getWidth(); x++) {
                b.append(board.getPiece(x, y)).append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }
}