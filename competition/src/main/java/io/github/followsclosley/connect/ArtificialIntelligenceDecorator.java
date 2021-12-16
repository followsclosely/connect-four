package io.github.followsclosley.connect;

public class ArtificialIntelligenceDecorator implements ArtificialIntelligence {

    private final ArtificialIntelligence parent;
    private long duration = 0;

    public ArtificialIntelligenceDecorator(ArtificialIntelligence parent) {
        this.parent = parent;
    }

    @Override
    public void initialize(int opponent) {
        parent.initialize(opponent);
    }

    @Override
    public int getColor() {
        return parent.getColor();
    }

    @Override
    public int yourTurn(Board board) {
        long start = System.currentTimeMillis();
        int turn = parent.yourTurn(board);
        synchronized (parent) {
            this.duration += System.currentTimeMillis() - start;
        }
        return turn;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return parent.getName();
    }

    public ArtificialIntelligence getParent() {
        return parent;
    }
}
