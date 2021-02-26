package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private Coordinate move;
    private List<Line> lines = new ArrayList<>();
    private List<Line> linesOpen = new ArrayList<>();

    public Turn(Coordinate move) {
        this.move = move;
    }

    public Coordinate getMove() { return move; }
    public List<Line> getLines() { return lines; }
    public List<Line> getLinesOpen() { return linesOpen; }

    public class Line {
        private List<Coordinate> coordinates = new ArrayList<>();

        public List<Coordinate> getCoordinates() { return coordinates; }
    }
}