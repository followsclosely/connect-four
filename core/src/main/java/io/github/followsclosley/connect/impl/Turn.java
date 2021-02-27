package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private Coordinate move;
    private List<Line> lines = new ArrayList<>();
    private List<Line> linesOpenForward = new ArrayList<>();
    private List<Line> linesOpenBackwards = new ArrayList<>();

    public Turn(Coordinate move) {
        this.move = move;
    }

    public Coordinate getMove() {
        return move;
    }

    public List<Line> getLines() {
        return lines;
    }

    public boolean isWinner(int goal) {
        for (Line line : lines) {
            if (line.getPieceCount() >= goal) {
                return true;
            }
        }
        return false;
    }

    public class Line {
        private List<Coordinate> coordinates = new ArrayList<>();
        private List<Coordinate> openForward = new ArrayList<>();
        private List<Coordinate> openBackwards = new ArrayList<>();

        public Line(Coordinate move) {
            coordinates.add(move);
        }

        public List<Coordinate> getCoordinates() {
            return coordinates;
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
            return coordinates.size();
        }

        public boolean isOpenOnBothEnds() {
            return (openForward.size() > 0 && openBackwards.size() > 0);
        }

        public int getPotential() {
            return coordinates.size() + openForward.size() + openBackwards.size();
        }
    }
}