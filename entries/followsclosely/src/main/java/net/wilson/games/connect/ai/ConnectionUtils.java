package net.wilson.games.connect.ai;

import net.wilson.games.connect.Coordinate;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionUtils {

    public static Map<String, Details> getConnectionDetails(MutableBoard board, Coordinate lastTurn) {

        int goal = board.getWidth();
        Map<String, Details> connections = new HashMap<>();

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = board.getPiece(x, y);

        // Horizontal First
        Details horizontal = new Details(lastTurn, color);
        for (int i = 1; i < goal && x - i >= 0 && horizontal.isEmptyOrMineForward(board.getPiece(x - i, y)); horizontal.add(new Coordinate(x - i, y)), i++)
            ;
        for (int i = 1; i < goal && x + i < board.getWidth() && horizontal.isEmptyOrMineBackwards(board.getPiece(x + i, y)); horizontal.add(new Coordinate(x + i, y)), i++)
            ;
        if (horizontal.getPieceCount() > 1) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        Details vertical = new Details(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && vertical.isEmptyOrMineForward(board.getPiece(x, y - i)); vertical.add(new Coordinate(x, y - i)), i++)
            ;
        for (int i = 1; i < goal && y + i < board.getHeight() && vertical.isEmptyOrMineBackwards(board.getPiece(x, y + i)); vertical.add(new Coordinate(x, y + i)), i++)
            ;
        if (vertical.getPieceCount() > 1) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        Details forwardSlash = new Details(lastTurn, color);
        for (int i = 1; i < goal && y + i < board.getHeight() && x - i >= 0 && forwardSlash.isEmptyOrMineForward(board.getPiece(x - i, y + i)); forwardSlash.add(new Coordinate(x - i, y + i)), i++)
            ;
        for (int i = 1; i < goal && y - i >= 0 && x + i < board.getWidth() && forwardSlash.isEmptyOrMineBackwards(board.getPiece(x + i, y - i)); forwardSlash.add(new Coordinate(x + i, y - i)), i++)
            ;
        if (forwardSlash.getPieceCount() > 1) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        Details backSlash = new Details(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && x - i >= 0 && backSlash.isEmptyOrMineForward(board.getPiece(x - i, y - i)); backSlash.add(new Coordinate(x - i, y - i)), i++)
            ;
        for (int i = 1; i < goal && y + i < board.getHeight() && x + i < board.getWidth() && backSlash.isEmptyOrMineBackwards(board.getPiece(x + i, y + i)); backSlash.add(new Coordinate(x + i, y + i)), i++)
            ;
        if (backSlash.getPieceCount() > 1) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }

    public static class Details {

        private int color;

        private int pieceCountForward = 1;
        private int pieceCountBackward = 1;

        private int emptyCountForward = 0;
        private int emptyCountBackward = 0;

        List<Coordinate> coordinates = new ArrayList<>();

        Details(Coordinate coordinate, int color) {
            coordinates.add(coordinate);
            this.color = color;
        }

        boolean isEmptyOrMineForward(int color) {
            if (color == 0) {
                emptyCountForward++;
                return true;
            } else if (color == this.color) {
                pieceCountForward++;
                return true;
            } else return false;
        }
        boolean isEmptyOrMineBackwards(int color) {
            if (color == 0) {
                emptyCountBackward++;
                return true;
            } else if (color == this.color) {
                pieceCountBackward++;
                return true;
            } else return false;
        }

        void add(Coordinate coordinate) {
            coordinates.add(coordinate);
        }

        public int getPieceCount() { return pieceCountForward+pieceCountBackward; }
        public int getPieceCountForward() { return pieceCountForward; }
        public int getPieceCountBackward() { return pieceCountBackward; }

        public int getEmptyCount() {
            return emptyCountForward+emptyCountBackward;
        }
        public int getEmptyCountForward() { return emptyCountForward; }
        public int getEmptyCountBackward() { return emptyCountBackward; }
    }
}