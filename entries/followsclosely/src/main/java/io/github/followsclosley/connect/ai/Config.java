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

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getLooser() {
        return looser;
    }

    public void setLooser(int looser) {
        this.looser = looser;
    }

    public int getLooserInTwo() {
        return looserInTwo;
    }

    public void setLooserInTwo(int looserInTwo) {
        this.looserInTwo = looserInTwo;
    }

    public int getLooserInOne() {
        return looserInOne;
    }

    public void setLooserInOne(int looserInOne) {
        this.looserInOne = looserInOne;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getYourColorInRow() {
        return yourColorInRow;
    }

    public void setYourColorInRow(int yourColorInRow) {
        this.yourColorInRow = yourColorInRow;
    }

    public int getEmptyInRow() {
        return emptyInRow;
    }

    public void setEmptyInRow(int emptyInRow) {
        this.emptyInRow = emptyInRow;
    }
}
