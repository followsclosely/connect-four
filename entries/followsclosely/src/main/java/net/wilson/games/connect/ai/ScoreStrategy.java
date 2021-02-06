package net.wilson.games.connect.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.Coordinate;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.Map;

/**
 * This strategy will assign a score to each option, then select the best option.
 * Wins 99.25% of games against a random AI.
 */
public class ScoreStrategy extends ArtificialIntelligence {

    private StringBuffer notes = new StringBuffer();
    private int[] opponents;

    public ScoreStrategy(int color, int... opponents) {
        super(color);
        this.opponents = opponents;
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

        //If you can win its worth 1,000 points, if so just return as the rest does not matter.
        if (!board.getWinningConnections().isEmpty()) {
            score += scoring.getWinner();
            notes.append(String.format(" + Winner(+%d) = %d", scoring.getWinner(), score));
            return score;
        }

        //Check out what happens is your opponent places a piece here instead of you
        for(int opponent : opponents) {
            //Undo the last move and replace the piece with your opponent
            Coordinate undo = board.undo();
            board.dropPiece(undo.getX(), opponent);

            //If your opponent can win its worth -500 points.
            if (!board.getWinningConnections().isEmpty()) {
                score += scoring.getLooser();
                notes.append(String.format(" + Looser(-%d) = %d", scoring.getWinner(), score));
            }

            Map<String, ConnectionUtils.Details> details = ConnectionUtils.getConnectionDetails(board, lastTurn);
            for (ConnectionUtils.Details detail : details.values()) {
                if ((detail.getEmptyCount() + detail.getPieceCount()) >= board.getGoal()) {
                    if( detail.getPieceCountConnected() ==3 && detail.getBackward().getEmptyCount() >0 && detail.getForward().getEmptyCount()>0){
                        score =+ scoring.getLooserInTwo();
                        notes.append(String.format(" + LooserInTwo(+%d)", scoring.getLooserInTwo()));
                    }
                }
            }

            //Place the board back into the original state
            board.undo();
            board.dropPiece(undo.getX(), getColor());
        }

        //Look for possible connect 2,3 or more with gaps
        Map<String, ConnectionUtils.Details> details = ConnectionUtils.getConnectionDetails(board, lastTurn);
        for (ConnectionUtils.Details detail : details.values()) {
            if ((detail.getEmptyCount() + detail.getPieceCount()) >= board.getGoal()) {
                score += (detail.getPieceCount() * scoring.getYourColorInRow())
                        + (detail.getEmptyCount() * scoring.getEmptyInRow());
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

    private Config scoring = new Config();
    public static class Config{
        private int winner = 10000;
        private int looser = 2000;
        private int looserInTwo = 200;
        private int center = 10;
        private int yourColorInRow = 2;
        private int emptyInRow = 1;

        public int getWinner() { return winner; }
        public int getCenter() { return center; }
        public int getYourColorInRow() { return yourColorInRow; }
        public int getEmptyInRow() { return emptyInRow; }
        public int getLooser() { return looser; }
        public int getLooserInTwo() { return looserInTwo; }
    }
}