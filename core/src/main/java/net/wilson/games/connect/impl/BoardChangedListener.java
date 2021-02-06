package net.wilson.games.connect.impl;

import net.wilson.games.connect.Coordinate;

public interface BoardChangedListener {
    void boardChanged(Coordinate coordinate);
}
