package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public class OneMorePieceToWinGrader extends AbstractGrader {

    public OneMorePieceToWinGrader(){
        super(25);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {

        int score = 0;
        int goalMinusOne = board.getGoal() - 1;

        for (Turn.Line line : thisTurn.getLines()) {
            //Look for possible connect 3 with empty on both sides
            if( line.getPieceCount() == goalMinusOne && line.isOpenOnBothEnds()){
                score += weight;
                //todo: Make a config entry and note this.
            }

            //Look for possible connect 2,3 or more with gaps
            if (line.getPotential() >= board.getGoal()) {
                score += (line.getPieceCount() * scoring.getYourColorInRow())
                        + (line.getEmptyCount() * scoring.getEmptyInRow());
                notes.append(String.format(" + InRow(%d*2) + EmptyInRow(%d)", line.getPieceCount(), line.getEmptyCount()));
            }
        }

    }
}