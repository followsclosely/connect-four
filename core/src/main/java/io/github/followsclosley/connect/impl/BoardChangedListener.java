package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Coordinate;

public interface BoardChangedListener {
    void boardChanged(Coordinate coordinate);
}
