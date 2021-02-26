package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

/**
 * This strategy will assign a score to each option, then select the best option.
 * Wins 99.92% of games against a random AI.
 */
public class ScoreStrategy implements ArtificialIntelligence {

    private int color;
    private StringBuffer notes = new StringBuffer();
    private int[] opponents;
    private Config scoring = new Config();

    public ScoreStrategy(int color, int... opponents) {
        this.color = color;
        this.opponents = opponents;
    }

    @Override
    public int getColor() {
        return color;
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

    public int scoreMove(MutableBoard board) {
        return scoreMove(board, board.getLastMove());
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn) {

        int score = 1;

        //System.out.println(String.format("Score (%d):", score));

        //Coordinate lastTurn = new Coordinate(x, y);

        notes.append(" +1 ");

        //Center column is worth 10 points
        int center = board.getWidth() / 2;
        if (lastTurn.getX() == center) {
            score += scoring.getCenter();
            notes.append(String.format(" + Center(+%d) ", scoring.getCenter()));
        }

        //If you can win its worth 1,000 points, if so just return as the rest does not matter.

        if (!TurnUtils.getWinningConnections(board).getLines().isEmpty()) {
            score += scoring.getWinner();
            notes.append(String.format(" + Winner(+%d) = %d", scoring.getWinner(), score));
            return score;
        }

        //Check if the computer can win next turn
        for (int opponentColor : opponents) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, opponentColor);
                    if (!TurnUtils.getWinningConnections(board).getLines().isEmpty()) {
                        score = +scoring.getLooserInOne();
                        notes.append(String.format(" + LooserInOne(+%d)", scoring.getLooserInOne()));
                    }
                    board.undo();
                }
            }
        }

        //Check out what happens is your opponent places a piece here instead of you
        for (int opponent : opponents) {
            //Undo the last move and replace the piece with your opponent
            Coordinate undo = board.undo();
            board.dropPiece(undo.getX(), opponent);

            //If your opponent can win its worth -500 points.
            if (!TurnUtils.getWinningConnections(board).getLines().isEmpty()) {
                score += scoring.getLooser();
                notes.append(String.format(" + Looser(-%d) = %d", scoring.getWinner(), score));
            }

            Turn turn = TurnUtils.getAllConnections(board);
            for (Turn.Line line : turn.getLines()) {
                if (line.getPieceCount() == 3 && line.isOpenOnBothEnds() ) {
                    score = +scoring.getLooserInTwo();
                    notes.append(String.format(" + LooserInTwo(+%d)", scoring.getLooserInTwo()));
                }
            }

            //Place the board back into the original state
            board.undo();
            board.dropPiece(undo.getX(), getColor());
        }

        //Look for possible connect 2,3 or more with gaps
        Turn turn = TurnUtils.getAllConnections(board);
        for (Turn.Line line : turn.getLines()) {
            if (line.getPotential() >= board.getGoal()) {
                score += (line.getPieceCount() * scoring.getYourColorInRow())
                        + (line.getEmptyCount() * scoring.getEmptyInRow());
                notes.append(String.format(" + InRow(%d*2) + EmptyInRow(%d)", line.getPieceCount(), line.getEmptyCount()));
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
}