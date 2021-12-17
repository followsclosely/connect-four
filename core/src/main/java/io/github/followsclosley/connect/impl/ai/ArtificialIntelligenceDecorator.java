package io.github.followsclosley.connect.impl.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;

public class ArtificialIntelligenceDecorator implements ArtificialIntelligence {

    protected final ArtificialIntelligence parent;

    public ArtificialIntelligenceDecorator(ArtificialIntelligence parent) {
        this.parent = parent;
    }

    @Override
    public void initialize(int opponent) {
        this.parent.initialize(opponent);
    }

    @Override
    public int getColor() {
        return this.parent.getColor();
    }

    @Override
    public int yourTurn(Board board) {
        return this.parent.yourTurn(board);
    }

    @Override
    public String getName() {
        return this.parent.getName();
    }
}
