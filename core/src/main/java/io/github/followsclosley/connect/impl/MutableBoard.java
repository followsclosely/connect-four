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
        super(width, height, goal);
    }

    public MutableBoard(Board board) {
        this.goal = board.getGoal();
        this.turns = new ArrayList<>(board.getTurns());
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x, y);
                if (this.state[x][y] == 0) {
                    this.turnsLeft++;
                }
            }
        }
    }

    public static MutableBoard initialize(String config) {
        return initialize(new MutableBoard(), config);
    }

    public static MutableBoard initialize(MutableBoard board, String config) {
        int index = config.length() - 1;
        for (int y = board.getHeight() - 1; y >= 0 && index >= 0; y--) {
            for (int x = board.getWidth() - 1; x >= 0 && index >= 0; x--, index--) {
                char c = config.charAt(index);
                if (c != '0' && c != ' ' && c != '-') {
                    int color = Character.getNumericValue(c);
                    board.dropPiece(x, color);
                }
            }
        }

        return board;
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
