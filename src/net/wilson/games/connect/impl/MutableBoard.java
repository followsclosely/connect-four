package net.wilson.games.connect.impl;

import net.wilson.games.common.Coordinate;
import net.wilson.games.connect.Board;

import java.util.ArrayList;
import java.util.Collections;

public class MutableBoard extends AbstractBoard {

    public MutableBoard(){
        this(7,6, 4);
    }
    public MutableBoard(int width, int height, int goal){
        this.state = new int[this.width = width][this.height = height];
        this.goal = goal;
        this.turns = new ArrayList<>();
    }

    public MutableBoard(Board board){
        this.goal = board.getGoal();
        this.turns = Collections.unmodifiableList(board.getTurns());
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for(int y=0; y<height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x,y);
            }
        }
    }

    public boolean canDropPiece(int x){
        return state[x][0] == 0;
    }

    public int dropPiece(int x, int piece)
    {
        for(int y = getHeight()-1; y>=0; y--){
            if( getPiece(x,y) == 0){
                state[x][y] = piece;
                turns.add(new Coordinate(x,y));
                return y;
            }
        }

        return -1;
    }

    /**
     * Determines if the piece at x,y just caused the game to end.
     *
     * todo: It would be nice if this method would return back a list of coordinates if there is a winner.
     *
     * @return -1 if no winner found, else returns the winning index/color
     */
    public int getWinner(Coordinate lastTurn) {

        boolean winner = false;

        Board board = this;

        int x = lastTurn.getX();
        int y = lastTurn.getY();
        int color = getPiece(x, y);

        // Horizontal First
        int horizontalCount = 1;
        for (int i=1; i<goal && x-i >= 0 && board.getPiece(x-i,y) == color; i++, horizontalCount++);
        for (int i=1; i<goal && x+i < board.getWidth() && board.getPiece(x+i,y) == color; i++, horizontalCount++);
        if( horizontalCount >= goal) {
            winner = true;
            System.out.println("We have a horizontal winner ("+color+") ["+x+","+y+"]!");
        }

        // Vertical
        int verticalCount = 1;
        for (int i=1; i<goal && y-i >= 0 && board.getPiece(x,y-i) == color; i++, verticalCount++);
        for (int i=1; i<goal && y+i < board.getHeight() && board.getPiece(x,y+i) == color; i++, verticalCount++);
        if( verticalCount >= goal) {
            winner = true;
            System.out.println("We have a vertical winner ("+color+") ["+x+","+y+"]!");
        }

        // Forward Slash Diagonal /
        int forwardSlashCount = 1;
        for (int i=1; i<goal && y+i < board.getHeight() && x-i >= 0         && board.getPiece(x-i,y+i) == color; i++, forwardSlashCount++);
        for (int i=1; i<goal && y-i >=0           && x+i < board.getWidth() && board.getPiece(x+i,y-i) == color; i++, forwardSlashCount++);
        if( forwardSlashCount >= goal) {
            winner = true;
            System.out.println("We have a / winner ("+color+") ["+x+","+y+"]!");
        }

        //Back Slash Diagonal \
        int backSlashCount = 1;
        for (int i=1; i<goal && y-i >= 0          && x-i >= 0         && board.getPiece(x-i,y-i) == color; i++, backSlashCount++);
        for (int i=1; i<goal && y+i < board.getHeight() && x+i < board.getWidth() && board.getPiece(x+i,y+i) == color; i++, backSlashCount++);
        if( backSlashCount >= goal) {
            winner = true;
            System.out.println("We have a \\ winner ("+color+") ["+x+","+y+"]!");
        }

        return (winner) ? color : -1;
    }
}