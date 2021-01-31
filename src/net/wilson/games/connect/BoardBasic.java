package net.wilson.games.connect;

import java.awt.geom.Dimension2D;

public class BoardBasic implements Board {
    int state[][] = new int[7][6];
    int last[] = new int[2];

    public BoardBasic(){
    }

    @Override
    public int getWidth(){ return 7; }
    @Override
    public int getHeight() { return 6; }

    @Override
    public int getPiece(int x, int y){
       return state[x][y];
    }

    @Override
    public boolean canDropPiece(int x){
        return (state[x][0] == 0);
    }

    public boolean dropPiece(int x, int piece)
    {
        int y = 5;
        for(; y>=0; y--){
            if( getPiece(x,y) == 0){
                state[last[0] = x][last[1] = y] = piece;
                return true;
            }
        }

        return false;
    }

    public boolean getWinner() {

        boolean winner = false;

        int x = last[0], y = last[1];
        int color = state[x][y];

        // Horizontal First
        int horizontalCount = 1;
        for (int i=1; i<4 && x-i >= 0 && state[x-i][y] == color; i++, horizontalCount++);
        for (int i=1; i<4 && x+i < getWidth() && state[x+i][y] == color; i++, horizontalCount++);
        if( horizontalCount >= 4) {
            winner = true;
            System.out.println("We have a horizontal winner ("+color+") ["+x+","+y+"]!");
        }

        // Vertical
        int verticalCount = 1;
        for (int i=1; i<4 && y-i >= 0 && state[x][y-i] == color; i++, verticalCount++);
        for (int i=1; i<4 && y+i < getHeight() && state[x][y+i] == color; i++, verticalCount++);
        if( verticalCount >= 4) {
            winner = true;
            System.out.println("We have a vertical winner ("+color+") ["+x+","+y+"]!");
        }

        // Forward Slash Diagonal /
        int forwardSlashCount = 1;
        for (int i=1; i<4 && y+i < getHeight() && x-i >= 0         && state[x-i][y+i] == color; i++, forwardSlashCount++);
        for (int i=1; i<4 && y-i >=0           && x+i < getWidth() && state[x+i][y-i] == color; i++, forwardSlashCount++);
        if( forwardSlashCount >= 4) {
            winner = true;
            System.out.println("We have a / winner ("+color+") ["+x+","+y+"]!");
        }

        //Back Slash Diagonal \
        int backSlashCount = 1;
        for (int i=1; i<4 && y-i >= 0          && x-i >= 0         && state[x-i][y-i] == color; i++, backSlashCount++);
        for (int i=1; i<4 && y+i < getHeight() && x+i < getWidth() && state[x+i][y+i] == color; i++, backSlashCount++);
        if( backSlashCount >= 4) {
            winner = true;
            System.out.println("We have a \\ winner ("+color+") ["+x+","+y+"]!");
        }

        return winner;
    }

    public String toMatrixString() {
        StringBuffer b = new StringBuffer();
        for(int y=0; y<getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                b.append(String.format("[%d,%d]  ",x,y));
            }
            b.append("\n");
        }
        return b.toString();
    }

    public String toString(){
        StringBuffer b = new StringBuffer("----------------\nboard = ");
        for (int x = 0; x < getWidth(); x++) {
            b.append(x + " ");
        }
        b.append("\n");

        for(int y=0; y<getHeight(); y++) {
            b.append("     " + y + ": ");
            for (int x = 0; x < getWidth(); x++) {
                b.append(getPiece(x, y) + " ");
            }
            b.append("\n");
        }
        return b.toString();
    }
}