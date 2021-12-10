package io.github.followsclosley.connect.ai.score.grader;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

public class BoardGrader {

    public static final int MAX_WINNING_SCORE = 777777;
    private static final Coordinate[] DIRECTIONS_TO_SEARCH = {
            new Coordinate(0, -1),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    };

    public int grade(Board board, int player1, int player2) {
        int finalScore = 0;

        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                int piece = board.getPiece(x, y);

                if (piece == player1) {
                    int score = scorePiece(board, piece, x, y);
                    //System.out.printf("[%d,%d] = %d\n", x, y, score);
                    finalScore += score;
                }

                if (piece == player2) {
                    int score = scorePiece(board, piece, x, y);
                    //System.out.printf("[%d,%d] = %d\n", x, y, score);
                    finalScore -= score;
                }

            }
        }
        return finalScore;
    }

    public int scorePiece(Board b, int color, int px, int py) {

        int totalScore = 0;

        for (Coordinate d : DIRECTIONS_TO_SEARCH) {

            int streakCount = 0;
            int emptyBackwardsCount = 0;
            int emptyForwardsCount = 0;

            for (int i = -1; i < 2; i += 2) {
                boolean streakAlive = true;
                boolean emptyAlive = true;
                for (int x = px + (d.getX() * i), y = py + (d.getY() * i); emptyAlive && x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x += (d.getX() * i), y += (d.getY() * i)) {

                    if (streakAlive && b.getPiece(x, y) == color) {
                        streakCount++;
                    } else {
                        streakAlive = false;
                    }

                    if (b.getPiece(x, y) == color || b.getPiece(x, y) == 0) {
                        if (i == -1) {
                            emptyBackwardsCount++;
                        } else {
                            emptyForwardsCount++;
                        }

                    } else {
                        emptyAlive = false;
                    }
                }
            }

            if (streakCount >= b.getGoal()) {
                return MAX_WINNING_SCORE;
            }

            if ((streakCount + emptyBackwardsCount + emptyForwardsCount) > b.getGoal()) {
                totalScore += Math.pow(3, streakCount) + emptyBackwardsCount + emptyForwardsCount;
            }

            if (emptyBackwardsCount > 0 && emptyForwardsCount > 0) {
                totalScore *= 3;
            }
        }

        return totalScore;
    }
}