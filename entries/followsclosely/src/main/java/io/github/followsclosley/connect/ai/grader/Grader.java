package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;

public interface Grader {
    int score(MutableBoard board, Turn turn, int... opponents);
}