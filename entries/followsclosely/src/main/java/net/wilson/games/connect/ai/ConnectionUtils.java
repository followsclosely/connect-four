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
        for (int i = 1; i < goal && x - i >= 0 && horizontal.getForward().isEmptyOrMine(board.getPiece(x - i, y)); horizontal.add(new Coordinate(x - i, y)), i++) ;
        for (int i = 1; i < goal && x + i < board.getWidth() && horizontal.getBackward().isEmptyOrMine(board.getPiece(x + i, y)); horizontal.add(new Coordinate(x + i, y)), i++) ;
        if (horizontal.getPieceCount() > 1) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        Details vertical = new Details(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && vertical.getForward().isEmptyOrMine(board.getPiece(x, y - i)); vertical.add(new Coordinate(x, y - i)), i++) ;
        for (int i = 1; i < goal && y + i < board.getHeight() && vertical.getBackward().isEmptyOrMine(board.getPiece(x, y + i)); vertical.add(new Coordinate(x, y + i)), i++) ;
        if (vertical.getPieceCount() > 1) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        Details forwardSlash = new Details(lastTurn, color);
        for (int i = 1; i < goal && y + i < board.getHeight() && x - i >= 0 && forwardSlash.getForward().isEmptyOrMine(board.getPiece(x - i, y + i)); forwardSlash.add(new Coordinate(x - i, y + i)), i++) ;
        for (int i = 1; i < goal && y - i >= 0 && x + i < board.getWidth() && forwardSlash.getBackward().isEmptyOrMine(board.getPiece(x + i, y - i)); forwardSlash.add(new Coordinate(x + i, y - i)), i++) ;
        if (forwardSlash.getPieceCount() > 1) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        Details backSlash = new Details(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && x - i >= 0 && backSlash.getForward().isEmptyOrMine(board.getPiece(x - i, y - i)); backSlash.add(new Coordinate(x - i, y - i)), i++) ;
        for (int i = 1; i < goal && y + i < board.getHeight() && x + i < board.getWidth() && backSlash.getBackward().isEmptyOrMine(board.getPiece(x + i, y + i)); backSlash.add(new Coordinate(x + i, y + i)), i++) ;
        if (backSlash.getPieceCount() > 1) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }

    public static class Details {

        private Counts forwards = new Counts();
        private Counts backwards = new Counts();

        private int color;
        private int pieceCountConnected = 1;

        List<Coordinate> coordinates = new ArrayList<>();

        Details(Coordinate coordinate, int color) {
            coordinates.add(coordinate);
            this.color = color;
        }

        void add(Coordinate coordinate) {
            coordinates.add(coordinate);
        }

        public int getPieceCountConnected() { return pieceCountConnected; }
        public int getPieceCount() { return 1+forwards.pieceCount+backwards.pieceCount; }
        public int getEmptyCount() {
            return forwards.emptyCount+backwards.emptyCount;
        }

        public Counts getForward(){ return forwards; }
        public Counts getBackward(){ return backwards; }

        public class Counts {
            boolean connectionBroken = false;
            int pieceCount = 0;
            int emptyCount = 0;

            boolean isEmptyOrMine(int color) {
                if (color == 0) {
                    emptyCount++;
                    return true;
                } else if (color == color) {
                    if( !connectionBroken ){
                        pieceCountConnected++;
                    }
                    pieceCount++;
                    return true;
                } else {
                    connectionBroken = true;
                    return false;
                }
            }

            public int getEmptyCount() { return emptyCount; }
            public int getPieceCount() { return pieceCount; }
        }
    }
}