package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

import java.util.*;

public class ConnectionUtils {

    public static Map<String, List<Coordinate>> getWinningConnections(Board board) {
        if (board.getTurns().isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        return getWinningConnections(board, board.getTurns().get(board.getTurns().size() - 1));
    }

    /**
     * Determines if the piece at x,y just caused the game to end.
     */
    public static Map<String, List<Coordinate>> getWinningConnections(Board board, Coordinate lastTurn) {

        Map<String, List<Coordinate>> connections = new HashMap<>();

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = board.getPiece(x, y);

        // Horizontal First
        List<Coordinate> horizontal = new ArrayList<>(board.getGoal());
        horizontal.add(lastTurn);
        for (int i = 1; i < board.getGoal() && x - i >= 0 && board.getPiece(x - i, y) == color; horizontal.add(0, new Coordinate(x - i, y)), i++) ;
        for (int i = 1; i < board.getGoal() && x + i < board.getWidth() && board.getPiece(x + i, y) == color; horizontal.add(new Coordinate(x + i, y)), i++) ;
        if (horizontal.size() >= board.getGoal()) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        List<Coordinate> vertical = new ArrayList<>(board.getGoal());
        vertical.add(lastTurn);
        for (int i = 1; i < board.getGoal() && y - i >= 0 && board.getPiece(x, y - i) == color; vertical.add(0, new Coordinate(x, y - i)), i++) ;
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && board.getPiece(x, y + i) == color; vertical.add(new Coordinate(x, y + i)), i++) ;
        if (vertical.size() >= board.getGoal()) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        List<Coordinate> forwardSlash = new ArrayList<>(board.getGoal());
        forwardSlash.add(lastTurn);
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && x - i >= 0 && board.getPiece(x - i, y + i) == color; forwardSlash.add(0, new Coordinate(x - i, y + i)), i++) ;
        for (int i = 1; i < board.getGoal() && y - i >= 0 && x + i < board.getWidth() && board.getPiece(x + i, y - i) == color; forwardSlash.add(new Coordinate(x + i, y - i)), i++) ;
        if (forwardSlash.size() >= board.getGoal()) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        List<Coordinate> backSlash = new ArrayList<>(board.getGoal());
        backSlash.add(lastTurn);
        for (int i = 1; i < board.getGoal() && y - i >= 0 && x - i >= 0 && board.getPiece(x - i, y - i) == color; backSlash.add(0, new Coordinate(x - i, y - i)), i++) ;
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && x + i < board.getWidth() && board.getPiece(x + i, y + i) == color; backSlash.add(new Coordinate(x + i, y + i)), i++) ;
        if (backSlash.size() >= board.getGoal()) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }

    public static Map<String, Details> getConnectionDetails(Board board, Coordinate lastTurn) {

        int goal = board.getWidth();
        Map<String, Details> connections = new HashMap<>();

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = board.getPiece(x, y);

        // Horizontal First
        Details horizontal = new Details(lastTurn, color);
        for (int i = 1; i < board.getGoal() && x - i >= 0 && horizontal.getForward().isEmptyOrMine(board.getPiece(x - i, y)); horizontal.add(new Coordinate(x - i, y)), i++) ;
        for (int i = 1; i < board.getGoal() && x + i < board.getWidth() && horizontal.getBackward().isEmptyOrMine(board.getPiece(x + i, y)); horizontal.add(new Coordinate(x + i, y)), i++) ;
        if (horizontal.getPieceCount() > 1) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        Details vertical = new Details(lastTurn, color);
        for (int i = 1; i < board.getGoal() && y - i >= 0 && vertical.getForward().isEmptyOrMine(board.getPiece(x, y - i)); vertical.add(new Coordinate(x, y - i)), i++) ;
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && vertical.getBackward().isEmptyOrMine(board.getPiece(x, y + i)); vertical.add(new Coordinate(x, y + i)), i++) ;
        if (vertical.getPieceCount() > 1) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        Details forwardSlash = new Details(lastTurn, color);
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && x - i >= 0 && forwardSlash.getForward().isEmptyOrMine(board.getPiece(x - i, y + i)); forwardSlash.add(new Coordinate(x - i, y + i)), i++) ;
        for (int i = 1; i < board.getGoal() && y - i >= 0 && x + i < board.getWidth() && forwardSlash.getBackward().isEmptyOrMine(board.getPiece(x + i, y - i)); forwardSlash.add(new Coordinate(x + i, y - i)), i++) ;
        if (forwardSlash.getPieceCount() > 1) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        Details backSlash = new Details(lastTurn, color);
        for (int i = 1; i < board.getGoal() && y - i >= 0 && x - i >= 0 && backSlash.getForward().isEmptyOrMine(board.getPiece(x - i, y - i)); backSlash.add(new Coordinate(x - i, y - i)), i++) ;
        for (int i = 1; i < board.getGoal() && y + i < board.getHeight() && x + i < board.getWidth() && backSlash.getBackward().isEmptyOrMine(board.getPiece(x + i, y + i)); backSlash.add(new Coordinate(x + i, y + i)), i++) ;
        if (backSlash.getPieceCount() > 1) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }

    public static class Details {

        List<Coordinate> coordinates = new ArrayList<>();
        private Counts forwards = new Counts();
        private Counts backwards = new Counts();
        private int myColor;
        private int pieceCountConnected = 1;

        Details(Coordinate coordinate, int color) {
            coordinates.add(coordinate);
            this.myColor = color;
        }

        void add(Coordinate coordinate) {
            coordinates.add(coordinate);
        }

        public int getPieceCountConnected() {
            return pieceCountConnected;
        }

        public int getPieceCount() {
            return 1 + forwards.pieceCount + backwards.pieceCount;
        }

        public int getEmptyCount() {
            return forwards.emptyCount + backwards.emptyCount;
        }

        public Counts getForward() {
            return forwards;
        }

        public Counts getBackward() {
            return backwards;
        }

        public class Counts {
            boolean connectionBroken = false;
            int pieceCount = 0;
            int emptyCount = 0;

            boolean isEmptyOrMine(int color) {
                if (color == 0) {
                    emptyCount++;
                    return true;
                } else if (color == myColor) {
                    if (!connectionBroken) {
                        pieceCountConnected++;
                    }
                    pieceCount++;
                    return true;
                } else {
                    connectionBroken = true;
                    return false;
                }
            }

            public int getEmptyCount() {
                return emptyCount;
            }

            public int getPieceCount() {
                return pieceCount;
            }
        }
    }
}