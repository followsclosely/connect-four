package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public class WinnerGrader extends AbstractGrader {

    public WinnerGrader() {
        super(10000);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {
        //If you can win its worth 1,000 points, if so just return as the rest does not matter.
        return (thisTurn.hasWinningLine(board.getGoal())) ? weight : 0;
        //notes.append(String.format(" + Winner(+%d) = %d", scoring.getWinner(), score));

    }
}