package io.github.followsclosley.connect.ai;

public class Config {
    private int winner = 10000;
    private int looser = 2000;
    private int looserInTwo = 200;
    private int looserInOne = -400;
    private int center = 10;
    private int yourColorInRow = 2;
    private int emptyInRow = 1;

    public int getWinner() {
        return winner;
    }
    public int getCenter() {
        return center;
    }
    public int getYourColorInRow() {
        return yourColorInRow;
    }
    public int getEmptyInRow() {
        return emptyInRow;
    }
    public int getLooser() {
        return looser;
    }
    public int getLooserInTwo() {
        return looserInTwo;
    }
    public int getLooserInOne() {
        return looserInOne;
    }
}
