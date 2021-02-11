package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MutableBoard extends AbstractBoard {

    private Coordinate lastMove = null;

    private List<BoardChangedListener> listeners;

    public MutableBoard() {
        this(7, 6, 4);
    }

    public MutableBoard(int width, int height, int goal) {
        this.state = new int[this.width = width][this.height = height];
        this.goal = goal;
        this.turns = new ArrayList<>();
    }

    public MutableBoard(Board board) {
        this.goal = board.getGoal();
        this.turns = new ArrayList<>(board.getTurns());
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x, y);
            }
        }
    }

    public Coordinate getLastMove() {
        return this.lastMove;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public boolean canDropPiece(int x) {
        return state[x][0] == 0;
    }

    public int dropPiece(int x, int piece) {
        for (int y = getHeight() - 1; y >= 0; y--) {
            int color = getPiece(x, y);
            if (color == 0) {
                state[x][y] = piece;
                turns.add(this.lastMove = new Coordinate(x, y));
                fireBoardChanged(new Coordinate(x, y));

                return y;
            }
        }

        return -1;
    }

    public Coordinate undo() {
        if (turns.isEmpty()) {
            return null;
        }
        int lastIndex = turns.size() - 1;
        this.lastMove = turns.remove(lastIndex);
        state[this.lastMove.getX()][this.lastMove.getY()] = 0;
        fireBoardChanged(this.lastMove);

        return this.lastMove;
    }

    private void fireBoardChanged(Coordinate coordinate) {
        if (listeners != null && !listeners.isEmpty()) {
            for (BoardChangedListener listener : listeners) {
                listener.boardChanged(coordinate);
            }
        }
    }

    public synchronized void addBoardChangedListener(BoardChangedListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>(1);
        }
        listeners.add(listener);
    }
}
