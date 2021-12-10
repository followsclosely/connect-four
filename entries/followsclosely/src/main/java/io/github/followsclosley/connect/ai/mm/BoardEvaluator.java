package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.Board;

public class BoardEvaluator {

    public int score(final Board board, final int color) {
        int score = scoreVertical(board, color);
        score += scoreHorizontal(board, color);
        score += scoreForwardDiagonal(board, color);
        //score += scoreBackwardsDiagonal(board, color);
        return score;
    }

    /**
     * Note: This method currently assumes that the board does not have a height higher than (goal*2-1).
     *
     * @param board
     * @param color
     * @return
     */
    public int scoreVertical(final Board board, final int color) {
        int score = 0;

        //Check for potential wins at the row level
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            //System.out.println("Checking (x=" + x + ")...");
            int actual = 0;
            int potential = 0;
            boolean stillHasPotential = true;

            for (int y = board.getHeight() - 1; y >= 0 && stillHasPotential; y--) {

                int piece = board.getPiece(x, y);
                if (piece == color) {
                    actual++;
                    potential++;
                } else if (piece == 0) {
                    potential += (1 + y);
                    stillHasPotential = false;
                } else {
                    actual = 0;
                    potential = 0;
                    stillHasPotential = (y >= board.getGoal());
                }
                //System.out.printf("piece == %d [actual= %d, potential= %d]\n", piece, actual, potential);
            }

            score += calculate(actual, potential, 0, board.getGoal());
        }

        return score;
    }

    /**
     * Note: This method currently assumes that the board does not have a width wider than (goal*2-1).
     *
     * @param board
     * @param color
     * @return
     */
    public int scoreHorizontal(final Board board, final int color) {
        int score = 0;

        //Check for potential wins at the row level
        for (int y = board.getHeight() - 1; y >= 0; y--) {
            //System.out.println("Checking (y=" + y + ")...");
            int actual = 0;
            int potentialRight = 0;
            int potentialLeft = 0;
            boolean reset = false;
            boolean stillHasPotential = true;

            for (int x = 0, width = board.getWidth(); x < width && stillHasPotential; x++) {
                int piece = board.getPiece(x, y);
                if (piece == color) {
                    if (reset) {
                        reset = false;
                        actual = 1;
                    } else {
                        actual++;
                    }
                } else if (piece == 0) {
                    if (actual > 0) potentialRight++;
                    if (actual == 0) potentialLeft++;
                } else {
                    reset = true;
                    stillHasPotential = (x < (board.getWidth() - board.getGoal()));
                }
                //System.out.printf("piece == %d [potentialLeft=%d, actual=%d, potentialRight=%d]\n", piece, potentialLeft, actual, potentialRight);
            }

            score += calculate(actual, potentialLeft, potentialRight, board.getGoal());
        }

        return score;
    }



    /**
     * Note: This method currently assumes that the board does not have a width wider than (goal*2-1).
     *
     * @param board
     * @param color
     * @return
     */
    public int scoreForwardDiagonal(final Board board, final int color) {
        int score = 0;

        //Check for potential wins at the diagonal level, starting with the middle of the 7 width board
        for (int x = board.getGoal()-1, width = board.getWidth()-(board.getGoal()-1), height= board.getHeight(); x < width; x++) {

            for (int y = board.getHeight() - 1; y >= 0; y--) {
                //System.out.println("Checking (" + x + "," + y + ")...");

                if (board.getPiece(x, y) == color) {

                    int actual = 1;
                    int potentialRight = 0;
                    int potentialLeft = 0;
                    boolean stillHasPotential = true;

                    //Test going right
                    for (int dx = x + 1, dy = y - 1; dy >= 0 && dx < board.getWidth() && stillHasPotential; dx++, dy--) {
                        //System.out.println("  ...testing right " + (dx) + "," + (dy));
                        int piece = board.getPiece(dx, dy);

                        if (piece == color) {
                            actual++;
                        } else if (piece == 0) {
                            potentialRight++;
                        } else {
                            stillHasPotential = false;
                        }
                    }

                    stillHasPotential = true;
                    //Test going left
                    for (int dx = x - 1, dy = y + 1; dy < board.getWidth() && dx >=0 && stillHasPotential; dx--, dy++) {
                        //System.out.println("  ...testing left " + (dx) + "," + (dy));
                        int piece = board.getPiece(dx, dy);

                        if (piece == color) {
                            actual++;
                        } else if (piece == 0) {
                            potentialLeft++;
                        } else {
                            stillHasPotential = false;
                        }
                    }

                    score += calculate(actual, potentialLeft, potentialRight, board.getGoal());
                }
            }
        }

        return score;

    }

    /**
     * Note: This method currently assumes that the board does not have a width wider than (goal*2-1).
     *
     * @param board
     * @param color
     * @return
     */
    public int scoreBackwardsDiagonal(final Board board, final int color) {
        int score = 0;

        //Check for potential wins at the diagonal level, starting with the middle of the 7 width board
        for (int x = board.getGoal()-1, width = board.getWidth()-(board.getGoal()-1), height= board.getHeight(); x < width; x++) {

            for (int y = board.getHeight() - 1; y >= 0; y--) {
                //System.out.println("Checking (" + x + "," + y + ")...");

                if (board.getPiece(x, y) == color) {

                    int actual = 1;
                    int potentialRight = 0;
                    int potentialLeft = 0;
                    boolean stillHasPotential = true;

                    //Test going right
                    for (int dx = x + 1, dy = y + 1; dy < board.getWidth() && dx < board.getWidth() && stillHasPotential; dx++, dy++) {
                        //System.out.println("  ...testing right " + (dx) + "," + (dy));
                        int piece = board.getPiece(dx, dy);

                        if (piece == color) {
                            actual++;
                        } else if (piece == 0) {
                            potentialRight++;
                        } else {
                            stillHasPotential = false;
                        }
                    }

                    stillHasPotential = true;
                    //Test going left
                    for (int dx = x - 1, dy = y - 1; dy >=0 && dx >=0 && stillHasPotential; dx--, dy--) {
                        //System.out.println("  ...testing left " + (dx) + "," + (dy));
                        int piece = board.getPiece(dx, dy);

                        if (piece == color) {
                            actual++;
                        } else if (piece == 0) {
                            potentialLeft++;
                        } else {
                            stillHasPotential = false;
                        }
                    }

                    score += calculate(actual, potentialLeft, potentialRight, board.getGoal());
                    System.out.println("score = " + score);
                }
            }
        }

        return score;

    }


    private int calculate(int actual, int potentialLeft, int potentialRight, int goal){
        int score = 0;

        if (actual >= goal) {
            score += 10000;
        }
        if (actual == goal-2 && potentialLeft>0 && potentialRight>0) {
            //Extra 10 points for a potential win on both sides in a row of 2.
            score += 10;
        }
        if (actual == goal -1 && potentialLeft>0 && potentialRight>0) {
            //Extra 100 points for a potential win on both sides in a row of 3.
            score += 100;
        }
        if (actual > 0 && (potentialLeft + actual + potentialRight) >= goal) {
            //Score a point for every line that has potential.
            score += (actual * 2);

            //One point per piece and one point for the open end.
            if (potentialLeft > 0) score += 1;
            if (potentialRight > 0) score += 1;

            //3 Points if both ends are open.
            if (potentialLeft > 0 && potentialRight > 0) score += 3;
        }

        return score;
    }
}
