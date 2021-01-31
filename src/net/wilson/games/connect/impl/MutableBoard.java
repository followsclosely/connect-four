package net.wilson.games.connect.impl;

import net.wilson.games.connect.Coordinate;
import net.wilson.games.connect.Board;

import java.util.*;

public class MutableBoard extends AbstractBoard {

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

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public boolean canDropPiece(int x) {
        return state[x][0] == 0;
    }

    public int dropPiece(int x, int piece) {
        for (int y = getHeight() - 1; y >= 0; y--) {
            if (getPiece(x, y) == 0) {
                state[x][y] = piece;
                turns.add(new Coordinate(x, y));
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
        Coordinate turn = turns.remove(lastIndex);
        state[turn.getX()][turn.getY()] = 0;

        return turn;
    }

    public Map<String, List<Coordinate>> getWinner() {
        if (turns.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        return getWinner(turns.get(turns.size() - 1));
    }

    /**
     * Determines if the piece at x,y just caused the game to end.
     * <p>
     * todo: It would be nice if this method would return back a list of coordinates if there is a winner.
     *
     * @return -1 if no winner found, else returns the winning index/color
     */
    public Map<String, List<Coordinate>> getWinner(Coordinate lastTurn) {

        Map<String, List<Coordinate>> connections = new HashMap<>();

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = getPiece(x, y);

        // Horizontal First
        List<Coordinate> horizontal = new ArrayList<>();
        for (int i = 1; i < goal && x - i >= 0         && getPiece(x - i, y) == color; horizontal.add(new Coordinate(x - i, y)), i++) ;
        for (int i = 1; i < goal && x + i < getWidth() && getPiece(x + i, y) == color; horizontal.add(new Coordinate(x + i, y)), i++) ;
        if (horizontal.size() >= goal) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        List<Coordinate> vertical = new ArrayList<>();
        for (int i = 1; i < goal && y - i >= 0          && getPiece(x, y - i) == color; vertical.add(new Coordinate(x, y - i)), i++) ;
        for (int i = 1; i < goal && y + i < getHeight() && getPiece(x, y + i) == color; vertical.add(new Coordinate(x, y + i)), i++) ;
        if (vertical.size() >= goal) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        List<Coordinate> forwardSlash = new ArrayList<>();
        for (int i = 1; i < goal && y + i < getHeight() && x - i >= 0         && getPiece(x - i, y + i) == color; forwardSlash.add(new Coordinate(x - i, y + i)), i++) ;
        for (int i = 1; i < goal && y - i >= 0          && x + i < getWidth() && getPiece(x + i, y - i) == color; forwardSlash.add(new Coordinate(x + i, y - i)), i++) ;
        if (forwardSlash.size() >= goal) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        List<Coordinate> backSlash = new ArrayList<>();
        for (int i = 1; i < goal && y - i >= 0          && x - i >= 0         && getPiece(x - i, y - i) == color; backSlash.add(new Coordinate(x - i, y - i)), i++) ;
        for (int i = 1; i < goal && y + i < getHeight() && x + i < getWidth() && getPiece(x + i, y + i) == color; backSlash.add(new Coordinate(x + i, y + i)), i++) ;
        if (backSlash.size() >= goal) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }
}