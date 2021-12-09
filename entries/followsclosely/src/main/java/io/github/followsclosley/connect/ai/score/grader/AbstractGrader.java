package io.github.followsclosley.connect.ai.score.grader;

public abstract class AbstractGrader implements Grader {
    protected int weight;

    public AbstractGrader(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
