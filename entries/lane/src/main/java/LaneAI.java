import graders.*;
import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LaneAI implements ArtificialIntelligence {

    private int playerColor;
    private int opponentColor;
    private int recursiveDepth;
    private List<Grader> graders;

    public LaneAI(int playerColor, int opponentColor, int recursiveDepth) {
        this.playerColor = playerColor;
        this.opponentColor = opponentColor;
        this.recursiveDepth = recursiveDepth;

        graders.add(new CenterColumnGrader());
        graders.add(new WinnerGrader());
        graders.add(new LoseNextTurnGrader());
        graders.add(new WinnerNextTurnGrader());
        graders.add(new OneMorePieceToWinGrader());
        graders.add(new InRowGrader());
        graders.add(new WhatIfOpponentWentHereGrader());
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
                playerScores = recursiveScoring(recursiveBoard, getRecursiveDepth(), playerScores, getColor());
                aggregateScore[i] = playerScores[0] - playerScores[1];
                Arrays.setAll(playerScores, p -> 0);
            }
        }

        return IntStream.range(0, aggregateScore.length)
                .reduce((x, y) -> aggregateScore[x] > aggregateScore[y] ? x : y)
                .getAsInt();
    }

    public int[] recursiveScoring(MutableBoard b, int depth, int[] playerScores, int color) {
        if (depth <= 0 || isFull(b)) {
            return playerScores;
        }

        int playerScoresIndex = color == getColor() ? 0 : 1;
        Turn thisTurn = new Turn(b.getLastMove());

        playerScores[playerScoresIndex] += scoreMove(b, b.getLastMove(), color == getColor() ? getOpponentColor() : getColor());

        if (thisTurn.hasWinningLine(b.getGoal())) {
            playerScores[playerScoresIndex] = 100000;
            return playerScores;
        }

        int nextColor = color == getColor() ? getOpponentColor() : getColor();
        int[] bestScoreAndIndex = getBestMove(b, nextColor);
        b.dropPiece(bestScoreAndIndex[1], nextColor);
        return recursiveScoring(b, depth - 1, playerScores, nextColor);
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn, int opponentColor) {

        int score = 0;

        Turn thisTurn = TurnUtils.getConnections(board);
        for (Grader grader : graders) {
            score += grader.score(board, thisTurn, opponentColor);
        }

        return score;
    }

    public int[] getBestMove(MutableBoard b, int color) {
        int[] moveScores = new int[b.getWidth()];
        int[] bestScoreAndIndex = new int[] {0, 0};
        Arrays.setAll(moveScores, p -> 0);

        for (int i = 0; i < b.getWidth(); i++) {
            if (b.canDropPiece(i)) {
                b.dropPiece(i, color);
                moveScores[i] = scoreMove(b, b.getLastMove(), color == getColor() ? getOpponentColor() : getColor());
                b.undo();
            }
        }

        for (int move : moveScores) {
            int counter = 0;
            if (move > moveScores[counter]) {
                bestScoreAndIndex[0] = move;
                bestScoreAndIndex[1] = counter;
            }
        }

        return bestScoreAndIndex;
    }

    public boolean isFull(MutableBoard board) {
       return board.getTurns().size() >= board.getWidth() * board.getHeight();
    }
}