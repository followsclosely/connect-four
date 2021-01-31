package net.wilson.games.connect;

import java.awt.geom.Dimension2D;

public class BoardBasic implements Board {

    int[][] state;
    int width, height;

    public BoardBasic(){
        this(7,6);
    }
    public BoardBasic(int width, int height){
        state = new int[this.width = width][this.height = height];
    }

    @Override
    public int getWidth(){ return this.width; }
    @Override
    public int getHeight() { return this.height; }

    @Override
    public int getPiece(int x, int y){
       return state[x][y];
    }

    @Override
    public boolean canDropPiece(int x){
        return (state[x][0] == 0);
    }

    public int dropPiece(int x, int piece)
    {
        for(int y = getHeight()-1; y>=0; y--){
            if( getPiece(x,y) == 0){
                state[x][y] = piece;
                return y;
            }
        }

        return -1;
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