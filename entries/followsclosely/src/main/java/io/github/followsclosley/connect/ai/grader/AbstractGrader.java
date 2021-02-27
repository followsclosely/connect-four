package io.github.followsclosley.connect.ai.grader;

public abstract class AbstractGrader implements Grader {
    protected int weight = 0;

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
