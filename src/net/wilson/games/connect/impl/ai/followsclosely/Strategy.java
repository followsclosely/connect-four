package net.wilson.games.connect.impl.ai.followsclosely;

import net.wilson.games.connect.impl.MutableBoard;

public interface Strategy {
    int yourTurn(MutableBoard board);
}