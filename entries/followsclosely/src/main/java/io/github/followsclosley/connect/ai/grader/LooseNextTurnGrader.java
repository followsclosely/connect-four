package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

public class LooseNextTurnGrader extends AbstractGrader {

    public LooseNextTurnGrader() {
        super(-2000);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {

        int score = 0;

        //Check if the computer can win next turn
        for (int opponentColor : opponents) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, opponentColor);
                    Turn turn = TurnUtils.getConnections(board);
                    if (turn.isWinner(board.getGoal())) {
                        score = +weight;
                        //notes.append(String.format(" + LooserInOne(+%d)", scoring.getLooserInOne()));
                    }
                    board.undo();
                }
            }
        }

        return score;
    }
}