package net.wilson.games.connect.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.Coordinate;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This strategy will assign a score to each option, then select the best option.
 * Alone wins around 93% of games against a random AI.
 */
public class ScoreStrategy extends ArtificialIntelligence {

    private StringBuffer notes = new StringBuffer();

    public ScoreStrategy(int color) {
        super(color);
    }

    @Override
    public int yourTurn(Board b) {

        MutableBoard board = new MutableBoard(b);

        int maxIndex = 0;
        int maxScore = -1;
        int[] scores = new int[board.getWidth()];

        for (int x = 0, width = board.getWidth(); x < width; x++) {

            if (board.canDropPiece(x)) {

                int y = board.dropPiece(x, getColor());

                scores[x] = scoreMove(board);
                if (maxScore < scores[x]) {
                    maxScore = scores[x];
                    maxIndex = x;
                }

                board.undo();
            }
        }

        //System.out.println(board);
        //System.out.println(Arrays.toString(scores));

        return maxIndex;
    }

    public int scoreMove(MutableBoard board){
        return scoreMove(board, board.getLastMove());
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn) {

        int score = -1;
        //System.out.println(String.format("Score (%d):", score));

        //Coordinate lastTurn = new Coordinate(x, y);

        notes.append(" -1 ");

        //Center column is worth 10 points
        int center = board.getWidth() / 2;
        if (lastTurn.getX() == center) {
            score += scoring.getCenter();
            notes.append(String.format(" + Center(+%d) ", scoring.getCenter()));
        }

        //If you can win its worth 1,000 points
        if (!board.getWinningConnections().isEmpty()) {
            score += scoring.getWinner();
            notes.append(String.format(" + Winner(+%d)", scoring.getWinner()));
        }

        //Look for possible connect 2,3 or more with gaps
        Map<String, ConnectionDetails> details = getConnectionDetails(board, lastTurn);
        for (ConnectionDetails detail : details.values()) {
            if( (detail.getEmptyCount() + detail.getPieceCount() ) >= board.getGoal()) {
                score += detail.getPieceCount() * scoring.getYourColorInRow() + detail.getEmptyCount();
                notes.append(String.format(" + InRow(%d*2) + EmptyInRow(%d)", detail.getPieceCount(), detail.getEmptyCount()));
            }
        }

        notes.append(String.format(" = %d", score));

        //System.out.println("========================");
        return score;
    }

    public String getNotes() {
        String contents = notes.toString();
        notes.delete(0, notes.length());
        return contents;
    }
    public Map<String, ConnectionDetails> getConnectionDetails(MutableBoard board, Coordinate lastTurn) {

        int goal = board.getWidth();
        Map<String, ConnectionDetails> connections = new HashMap<>();

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = board.getPiece(x, y);

        // Horizontal First
        ConnectionDetails horizontal = new ConnectionDetails(lastTurn, color);
        for (int i = 1; i < goal && x - i >= 0 && horizontal.isEmptyOrMine(board.getPiece(x - i, y)); horizontal.add(new Coordinate(x - i, y)), i++)
            ;
        for (int i = 1; i < goal && x + i < board.getWidth() && horizontal.isEmptyOrMine(board.getPiece(x + i, y)); horizontal.add(new Coordinate(x + i, y)), i++)
            ;
        if (horizontal.pieceCount > 1) {
            connections.put("Horizontal", horizontal);
        }

        // Vertical
        ConnectionDetails vertical = new ConnectionDetails(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && vertical.isEmptyOrMine(board.getPiece(x, y - i)); vertical.add(new Coordinate(x, y - i)), i++)
            ;
        for (int i = 1; i < goal && y + i < board.getHeight() && vertical.isEmptyOrMine(board.getPiece(x, y + i)); vertical.add(new Coordinate(x, y + i)), i++)
            ;
        if (vertical.pieceCount > 1) {
            connections.put("Vertical", vertical);
        }

        // Forward Slash Diagonal /
        ConnectionDetails forwardSlash = new ConnectionDetails(lastTurn, color);
        for (int i = 1; i < goal && y + i < board.getHeight() && x - i >= 0 && forwardSlash.isEmptyOrMine(board.getPiece(x - i, y + i)); forwardSlash.add(new Coordinate(x - i, y + i)), i++)
            ;
        for (int i = 1; i < goal && y - i >= 0 && x + i < board.getWidth() && forwardSlash.isEmptyOrMine(board.getPiece(x + i, y - i)); forwardSlash.add(new Coordinate(x + i, y - i)), i++)
            ;
        if (forwardSlash.pieceCount > 1) {
            connections.put("ForwardSlash", forwardSlash);
        }

        //Back Slash Diagonal \
        ConnectionDetails backSlash = new ConnectionDetails(lastTurn, color);
        for (int i = 1; i < goal && y - i >= 0 && x - i >= 0 && backSlash.isEmptyOrMine(board.getPiece(x - i, y - i)); backSlash.add(new Coordinate(x - i, y - i)), i++)
            ;
        for (int i = 1; i < goal && y + i < board.getHeight() && x + i < board.getWidth() && backSlash.isEmptyOrMine(board.getPiece(x + i, y + i)); backSlash.add(new Coordinate(x + i, y + i)), i++)
            ;
        if (backSlash.pieceCount > 1) {
            connections.put("BackSlash", backSlash);
        }

        return connections;
    }

    private class ConnectionDetails {

        private int color;
        private int pieceCount = 1;
        private int emptyCount = 0;

        List<Coordinate> coordinates = new ArrayList<>();

        ConnectionDetails(Coordinate coordinate, int color) {
            coordinates.add(coordinate);
            this.color = color;
        }

        boolean isEmptyOrMine(int color) {
            if (color == 0) {
                emptyCount++;
                return true;
            } else if (color == this.color) {
                pieceCount++;
                return true;
            } else return false;
        }

        void add(Coordinate coordinate) {
            coordinates.add(coordinate);
        }

        public int getPieceCount() {
            return pieceCount;
        }

        public int getEmptyCount() {
            return emptyCount;
        }
    }

    private Config scoring = new Config();
    public static class Config{
        private int winner = 1000;
        private int center = 5;
        private int yourColorInRow = 2;
        private int emptyInRow = 1;

        public int getWinner() { return winner; }
        public int getCenter() { return center; }
        public int getYourColorInRow() { return yourColorInRow; }
        public int getEmptyInRow() { return emptyInRow; }
    }
}