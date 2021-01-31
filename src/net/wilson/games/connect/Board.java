package net.wilson.games.connect;

import net.wilson.games.common.Coordinate;

import java.util.List;

public interface Board {
    int getGoal();
    int getWidth();
    int getHeight();

    int getPiece(int x, int y);
    List<Coordinate> getTurns();
}