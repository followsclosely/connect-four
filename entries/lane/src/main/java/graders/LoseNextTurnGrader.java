package graders;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

public class LoseNextTurnGrader extends AbstractGrader {

    public LoseNextTurnGrader() {
        super(1000);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int opponent) {

        int score = 0;

        //Check if the computer can win next turn

            for (int x = 0, width = board.getWidth(); x < width; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, opponent);
                    Turn turn = TurnUtils.getConnections(board);
                    if (turn.hasWinningLine(board.getGoal())) {
                        score = -weight;
                        //notes.append(String.format(" + LooserInOne(+%d)", scoring.getLooserInOne()));
                    }
                    board.undo();
            }
        }

        return score;
    }
}