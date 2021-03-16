package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class MiniMaxAlgorithm implements ArtificialIntelligence {

    private int color;
    private int[] colors;
    private int maxDepth = 3;
    private ScoreStrategy scoreStrategy;

    public MiniMaxAlgorithm(int color) {
        this.color = color;
        this.scoreStrategy = new ScoreStrategy(color);
    }

    @Override
    public void initialize(int opponent) {
        colors = new int[]{opponent};
        this.scoreStrategy.initialize(opponent);
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board b) {
        MutableBoard board = (b instanceof MutableBoard) ? (MutableBoard) b : new MutableBoard(b);
        MiniMaxAlgorithm.Node root = evaluate(board);
        //Sort by score.
        List<MiniMaxAlgorithm.Node> sortedList = root.getChildren().stream()
                .sorted(Comparator.comparing(MiniMaxAlgorithm.Node::getScore).reversed())
                .collect(Collectors.toList());

        MiniMaxAlgorithm.Node best = sortedList.get(0);

        return best.getColumn();
    }

    public Node evaluate(MutableBoard board) {
        Node root = new Node();
        root.evaluate(board);
        return root;
    }

    public class Node {
        private String notes;
        private int depth;
        private int column;
        private int score;
        private int minMaxScore;
        private IntBinaryOperator operator;

        private Node parent;
        private List<Node> children;

        public Node() {
            this.operator = Integer::max;
            this.minMaxScore = Integer.MIN_VALUE;
            this.children = new ArrayList<>();
        }

        public Node(Node parent, int column) {

            if ((parent.getDepth() % 2 == 0)) {
                this.operator = Integer::max;
                this.minMaxScore = Integer.MIN_VALUE;
            } else {
                this.operator = Integer::min;
                this.minMaxScore = Integer.MAX_VALUE;
            }

            this.depth = parent.getDepth() + 1;
            this.children = new ArrayList<>();
            this.parent = parent;
            this.column = column;
        }

        public void evaluate(MutableBoard board) {

            boolean gameWon = false;
            int color = colors[depth % 2];
            for (int x = 0, width = board.getWidth(); x < width && !gameWon; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, color);
                    Node node = new Node(this, x);
                    node.score = scoreStrategy.scoreMove(board, board.getLastMove(), null);

                    //System.out.println( node.column + " -> " + node.score);
                    children.add(node);

                    gameWon = TurnUtils.getConnections(board).hasWinningLine(board.getGoal());
                    if (gameWon || depth >= maxDepth) {
                        //if(!board.getWinningConnections().isEmpty())
                        node.rollUpScore(scoreStrategy.scoreMove(board, board.getLastMove(), null));
                        //}
                    } else {
                        node.evaluate(board);
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
            return "Node{" + "depth=" + depth + ", column=" + column + ", score=" + score + "/" + minMaxScore + '}';
        }
    }
}