package net.wilson.games.connect;

public interface Board {
    int getWidth();
    int getHeight();
    int getPiece(int x, int y);
    boolean canDropPiece(int x);
}
