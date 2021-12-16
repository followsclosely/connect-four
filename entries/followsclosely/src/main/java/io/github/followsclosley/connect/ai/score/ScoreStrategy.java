package io.github.followsclosley.connect.ai.score;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.ai.score.grader.*;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This strategy will assign a score to each option, then select the best option.
 * Wins 99.9% of games against a random AI.
 */
public class ScoreStrategy implements ArtificialIntelligence {

    private final int color;
    private final List<Grader> graders = new ArrayList<>();
    private int[] opponents;

    public ScoreStrategy(int color) {
        this.color = color;

        graders.add(new CenterColumnGrader());
        graders.add(new WinnerGrader());
        graders.add(new LooseNextTurnGrader());
        graders.add(new WinnerNextTurnGrader());
        graders.add(new OneMorePieceToWinGrader());
        graders.add(new InRowGrader());
        graders.add(new WhatIfOpponentWentHereGrader());
    }

    @Override
    public void initialize(int opponent) {
        this.opponents = new int[]{opponent};
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board b) {

        MutableBoard board = new MutableBoard(b);

        int maxIndex = 0;
        int maxScore = Integer.MIN_VALUE;
        int[] scores = new int[board.getWidth()];

        for (int x = 0, width = board.getWidth(); x < width; x++) {
            if (board.dropPiece(x, getColor()) >= 0 ) {
                scores[x] = scoreMove(board, board.getLastMove(), opponents);
                if (maxScore < scores[x]) {
                    maxScore = scores[x];
                    maxIndex = x;
                }
                board.undo();
            }
        }

        return maxIndex;
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn, int... opponents) {
        int score = 1;
        Turn thisTurn = TurnUtils.getConnections(board);
        for (Grader grader : graders) {
            score += grader.score(board, thisTurn, opponents);
        }

        return score;
    }
}