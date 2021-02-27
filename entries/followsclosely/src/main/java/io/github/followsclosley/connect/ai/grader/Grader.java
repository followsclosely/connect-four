package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public interface Grader {
    int score(MutableBoard board, Turn turn, int... opponents);
}
