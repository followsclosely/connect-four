package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * A Turn holds information about what would result or what has resulted.
 * This information includes information about the lines of connections.
 */
public class Turn {

    private Coordinate move;
    private List<Line> lines = new ArrayList<>();

    public Turn(Coordinate move) {
        this.move = move;
    }

    public Coordinate getMove() {
        return move;
    }

    public List<Line> getLines() {
        return lines;
    }

    public boolean hasWinningLine(int goal) {
        for (Line line : lines) {
            if (line.getPieceCount() >= goal) {
                return true;
            }
        }
        return false;
    }

    public class Line {
        private List<Coordinate> connected = new ArrayList<>();
        private List<Coordinate> openForward = new ArrayList<>();
        private List<Coordinate> openBackwards = new ArrayList<>();

        public Line(Coordinate move) {
            connected.add(move);
        }

        public List<Coordinate> getConnected() {
            return connected;
        }

        public List<Coordinate> getOpenForward() {
            return openForward;
        }

        public List<Coordinate> getOpenBackwards() {
            return openBackwards;
        }

        public int getEmptyCount() {
            return openForward.size() + openBackwards.size();
        }

        public int getPieceCount() {
            return connected.size();
        }

        public boolean isOpenOnBothEnds() {
            return (openForward.size() > 0 && openBackwards.size() > 0);
        }

        public int getPotential() {
            return connected.size() + openForward.size() + openBackwards.size();
        }
    }
}