package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

public class TurnUtils {

    private static final Coordinate[] DIRECTIONS_TO_SEARCH = {
            new Coordinate(0,-1),
            new Coordinate(1,-1),
            new Coordinate(1,0),
            new Coordinate(1,1)
    };

    public static Turn getTurn(Board b, Coordinate c, int color){
        Turn turn = new Turn(c);

        for( Coordinate d : DIRECTIONS_TO_SEARCH){

            Turn.Line line = turn.new Line();
            for (int x = c.getX() + d.getX(), y = c.getY() + d.getY(); x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x += d.getX(), y += d.getY()) {
                if( b.getPiece(x,y) == 0) {
                    line.getCoordinates().add(new Coordinate(x, y));
                }
            }
            for (int x = c.getX() - d.getX(), y = c.getY() - d.getY(); x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x -= d.getX(), y -= d.getY()) {
                if( b.getPiece(x,y) == 0) {
                    line.getCoordinates().add(new Coordinate(x, y));
                }
            }

            if( line.getCoordinates().size() >= b.getGoal()){
                turn.getLines().add(line);
            }
        }

        return turn;
    }
}