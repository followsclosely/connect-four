package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;

public class TurnUtils {

    private static final Coordinate[] DIRECTIONS_TO_SEARCH = {
            new Coordinate(0, -1),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    };

    public static Turn getConnections(Board b) {
        int turns = b.getTurns().size();
        if (turns > 0) {
            Coordinate turn = b.getTurns().get(turns - 1);
            return getConnections(b, turn, b.getPiece(turn.getX(), turn.getY()));
        } else {
            return new Turn(null);
        }
    }

    public static Turn getConnections(Board b, Coordinate c, int color) {
        Turn turn = new Turn(c);

        for (Coordinate d : DIRECTIONS_TO_SEARCH) {

            Turn.Line lineMyColor = turn.new Line(c);
            for (int i = -1; i < 2; i += 2) {
                boolean streakAlive = true;
                for (int x = c.getX() + (d.getX() * i), y = c.getY() + (d.getY() * i); x < b.getWidth() && x >= 0 && y < b.getHeight() && y >= 0; x += (d.getX() * i), y += (d.getY() * i)) {
                    Coordinate currentTurn = new Coordinate(x, y);
                    if (streakAlive && b.getPiece(x, y) == color) {
                        if (i == -1) {
                            lineMyColor.getConnected().add(0, currentTurn);
                        } else {
                            lineMyColor.getConnected().add(currentTurn);
                        }
                    } else {
                        streakAlive = false;
                    }
                    if (b.getPiece(x, y) == color || b.getPiece(x, y) == 0) {
                        ((i == -1) ? lineMyColor.getOpenBackwards() : lineMyColor.getOpenForward()).add(currentTurn);
                    }
                }
            }

            turn.getLines().add(lineMyColor);
        }

        return turn;
    }
}