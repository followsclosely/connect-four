package io.github.lane;

import io.github.followsclosley.connect.ai.grader.*;
import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LaneAI implements ArtificialIntelligence {

    private int playerColor;
    private int opponentColor;
    private int recursiveDepth;
    private List<Grader> firstMoveGraders = new ArrayList<>();
    private List<Grader> recursiveMoveGraders = new ArrayList<>();

    public LaneAI(int playerColor, int recursiveDepth) {
        this.playerColor = playerColor;
        this.recursiveDepth = recursiveDepth;

        firstMoveGraders.add(new CenterColumnGrader());
        firstMoveGraders.add(new WinnerGrader());
        firstMoveGraders.add(new LooseNextTurnGrader());
        firstMoveGraders.add(new WinnerNextTurnGrader());
        firstMoveGraders.add(new OneMorePieceToWinGrader());
        firstMoveGraders.add(new InRowGrader());
        firstMoveGraders.add(new WhatIfOpponentWentHereGrader());

        recursiveMoveGraders.add(new CenterColumnGrader());
        recursiveMoveGraders.add(new OneMorePieceToWinGrader());
        recursiveMoveGraders.add(new InRowGrader());
        recursiveMoveGraders.add(new WhatIfOpponentWentHereGrader());
    }

    @Override
    public void initialize(int opponent) {
        this.opponentColor = opponent;
    }

    @Override
    public int getColor() {
        return playerColor;
    }

    public int getOpponentColor() {
        return opponentColor;
    }

    public int getRecursiveDepth() {
        return recursiveDepth;
    }

    @Override
    public int yourTurn(Board b) {
        MutableBoard board = new MutableBoard(b);
        int[] aggregateScore = new int[board.getWidth()];
        Arrays.setAll(aggregateScore, p -> 0);

        int [] playerScores = new int[] {0, 0};

        for (int i = 0; i < b.getWidth(); i++) {
            MutableBoard recursiveBoard = new MutableBoard(board);
            if (recursiveBoard.canDropPiece(i)) {
                recursiveBoard.dropPiece(i, getColor());
                playerScores = recursiveScoring(recursiveBoard, getRecursiveDepth(), getRecursiveDepth(), playerScores, getColor());
                aggregateScore[i] = playerScores[0] - playerScores[1];
                Arrays.setAll(playerScores, p -> 0);
            }
        }

        return IntStream.range(0, aggregateScore.length)
                .reduce((x, y) -> aggregateScore[x] > aggregateScore[y] ? x : y)
                .getAsInt();
    }

    public int[] recursiveScoring(MutableBoard b, int depth, int maxDepth, int[] playerScores, int color) {
        if (depth <= 0 || isFull(b)) {
            return playerScores;
        }

        int playerScoresIndex = color == getColor() ? 0 : 1;
        Turn thisTurn = new Turn(b.getLastMove());

        playerScores[playerScoresIndex] += scoreMove(b, b.getLastMove(), color == getColor() ? getOpponentColor() : getColor(), depth == maxDepth);

        int nextColor = color == getColor() ? getOpponentColor() : getColor();
        int[] bestScoreAndIndex = getBestMove(b, nextColor, maxDepth == depth);
        b.dropPiece(bestScoreAndIndex[1], nextColor);
        return recursiveScoring(b, depth - 1, maxDepth, playerScores, nextColor);
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn, int color, boolean isTopMove) {

        int score = 0;

        Turn thisTurn = TurnUtils.getConnections(board);
        if (isTopMove) {
            for (Grader grader : firstMoveGraders) {
                score += grader.score(board, thisTurn, color);
            }
        } else {
            for (Grader grader : recursiveMoveGraders) {
                score += grader.score(board, thisTurn, color);
            }
        }

        return score;
    }

    public int[] getBestMove(MutableBoard b, int color, boolean isTopMove) {
        int[] bestScoreAndIndex = new int[] {0, 0};

        for (int i = 0; i < b.getWidth(); i++) {
            if (b.canDropPiece(i)) {
                b.dropPiece(i, color);
                int moveScore = scoreMove(b, b.getLastMove(), color == getColor() ? getOpponentColor() : getColor(), isTopMove);

                if (moveScore > bestScoreAndIndex[0]) {
                    bestScoreAndIndex[0] = moveScore;
                    bestScoreAndIndex[1] = i;
                }

                b.undo();
            }
        }

        return bestScoreAndIndex;
    }

    public boolean isFull(MutableBoard board) {
       return board.getTurns().size() >= board.getWidth() * board.getHeight();
    }
}