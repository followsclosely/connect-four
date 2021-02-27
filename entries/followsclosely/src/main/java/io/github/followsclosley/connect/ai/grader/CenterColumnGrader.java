package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public class CenterColumnGrader extends AbstractGrader {

    public CenterColumnGrader() {
        super(10);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {
        //Center column is worth 10 points
        int center = board.getWidth() / 2;
        return (thisTurn.getMove().getX() == center) ? weight : 0;
        //notes.append(String.format(" + Center(+%d) ", scoring.getCenter()));
    }
}