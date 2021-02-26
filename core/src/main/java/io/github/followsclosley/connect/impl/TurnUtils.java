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

    public static Turn getWinningConnections(Board b){
        int turns = b.getTurns().size();
        if( turns > 0){
            Coordinate turn = b.getTurns().get(turns-1);
            return getWinningConnections(b, turn, b.getPiece(turn.getX(), turn.getY() ));
        } else {
            return new Turn(null);
        }
    }

    public static Turn getWinningConnections(Board b, Coordinate c, int color){
        Turn turn = new Turn(c);

        for( Coordinate d : DIRECTIONS_TO_SEARCH){

            Turn.Line lineMyColor = turn.new Line(c);
            for( int i = -1; i<2; i+=2) {
                for (int x = c.getX() + (d.getX()*i), y = c.getY() + (d.getY()*i); x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x += (d.getX()*i), y += (d.getY()*i)) {
                    Coordinate currentTurn = new Coordinate(x, y);
                    if (b.getPiece(x, y) == color) {
                        lineMyColor.getCoordinates().add(currentTurn);
                    }
                }
            }

            if( lineMyColor.getCoordinates().size() >= b.getGoal() ) {
                turn.getLines().add(lineMyColor);
            }
        }

        return turn;
    }


    public static Turn getAllConnections(Board b){
        int turns = b.getTurns().size();
        if( turns > 0){
            Coordinate turn = b.getTurns().get(turns-1);
            return getWinningConnections(b, turn, b.getPiece(turn.getX(), turn.getY() ));
        } else {
            return new Turn(null);
        }
    }

    public static Turn getAllConnections(Board b, Coordinate c, int color){
        Turn turn = new Turn(c);

        for( Coordinate d : DIRECTIONS_TO_SEARCH){

            Turn.Line lineMyColor = turn.new Line(c);
            for( int i = -1; i<2; i+=2) {
                for (int x = c.getX() + (d.getX()*i), y = c.getY() + (d.getY()*i); x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x += (d.getX()*i), y += (d.getY()*i)) {
                    Coordinate currentTurn = new Coordinate(x, y);
                    if (b.getPiece(x, y) == color) {
                        lineMyColor.getCoordinates().add(currentTurn);
                    }
                    if (b.getPiece(x, y) == color || b.getPiece(x, y) == 0) {
                        ((i==-1) ? lineMyColor.getOpenBackwards(): lineMyColor.getOpenForward()).add(currentTurn);
                    }
                }
            }

            turn.getLines().add(lineMyColor);
        }

        return turn;
    }
}