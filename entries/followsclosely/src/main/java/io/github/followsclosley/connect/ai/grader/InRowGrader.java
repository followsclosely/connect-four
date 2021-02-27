package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public class InRowGrader extends AbstractGrader {

    public InRowGrader() {
        super(3);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {

        int score = 0;

        for (Turn.Line line : thisTurn.getLines()) {
            //Look for possible connect 2,3 or more with gaps
            if (line.getPotential() >= board.getGoal()) {
                score += (line.getPieceCount() * (getWeight() * 2));
                score += (line.getEmptyCount());
            }
        }

        return score;
    }
}