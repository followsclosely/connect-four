package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Board;

import java.util.Collections;

public class ImmutableBoard extends AbstractBoard implements Board {

    public ImmutableBoard(Board board) {
        this.goal = board.getGoal();
        this.turns = Collections.unmodifiableList(board.getTurns());
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x, y);
                if( this.state[x][y] == 0){
                    this.turnsLeft++;
                }
            }
        }
    }
}
