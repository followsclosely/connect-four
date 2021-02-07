package net.wilson.games.connect.ai;

import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class MiniMaxAlgorithm {

    private int color;
    private int[] colors;
    private int width;
    private int maxDepth = 5;
    private MutableBoard board;
    private ScoreStrategy scoreStrategy;

    public MiniMaxAlgorithm(MutableBoard board, int color, int... colors) {
        this.board = board;
        this.width = board.getWidth();
        this.colors = colors;
        this.scoreStrategy = new ScoreStrategy(color, colors);
    }

    public Node evaluate() {
        Node root = new Node();
        root.evaluate();
        return root;
    }

    public enum Mode {Mini, Max}

    public class Node {
        private String notes;
        private Mode mode;
        private int depth;
        private int column;
        private int score;
        private int minMaxScore;
        private IntBinaryOperator operator;

        private Node parent;
        private List<Node> children;

        public Node() {
            mode = Mode.Max;
            this.operator = Integer::max;
            this.minMaxScore = Integer.MIN_VALUE;
            this.children = new ArrayList<>(board.getWidth());
        }

        public Node(Node parent, int column) {

            if ((parent.getDepth() % 2 == 0)) {
                mode = Mode.Max;
                this.operator = Integer::max;
                this.minMaxScore = Integer.MIN_VALUE;
            } else {
                mode = Mode.Mini;
                this.operator = Integer::min;
                this.minMaxScore = Integer.MAX_VALUE;
            }

            this.depth = parent.getDepth() + 1;
            this.children = new ArrayList<>(board.getWidth());
            this.parent = parent;
            this.column = column;
        }

        public void evaluate() {

            boolean gameWon = false;
            int color = colors[depth % 2];
            for (int x = 0; x < width && !gameWon; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, color);
                    Node node = new Node(this, x);
                    if (node.mode == Mode.Max) {
                        node.score = scoreStrategy.scoreMove(board);
                        node.notes = scoreStrategy.getNotes();
                    }

                    //System.out.println( node.column + " -> " + node.score);
                    children.add(node);

                    gameWon = !board.getWinningConnections().isEmpty();
                    if (gameWon || depth >= maxDepth) {
                        //if(!board.getWinningConnections().isEmpty()) {
                        node.rollUpScore(scoreStrategy.scoreMove(board));
                        //}
                    } else {
                        node.evaluate();
                    }
                    board.undo();
                }
            }
        }

        public void rollUpScore(int score) {
            int newScore = operator.applyAsInt(minMaxScore, score);
            //If the score changed, then roll it on up.
            if (newScore != minMaxScore) {
                minMaxScore = newScore;
                if (parent != null) {
                    parent.rollUpScore(this.minMaxScore);
                }
            }
        }

        public int getDepth() {
            return depth;
        }

        public int getColumn() {
            return column;
        }

        public int getScore() {
            return score;
        }

        public List<Node> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "Node(" + mode + "){" + "depth=" + depth + ", column=" + column + ", score=" + score + "/" + minMaxScore + '}';
        }
    }
}