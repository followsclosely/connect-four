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
    private int opponent;
    private int[] colors;

    //Number of turns to look past the current one.
    private int maxDepth = 0;
    private ScoreStrategy scoreStrategy;

    public MiniMaxAlgorithm(int color, int depth) {
        this.color = color;
        this.maxDepth = depth;
        this.scoreStrategy = new ScoreStrategy(color);
    }

    @Override
    public void initialize(int opponent) {
        this.scoreStrategy.initialize(this.opponent = opponent);
        this.colors = new int[]{color,opponent};
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board b) {
        //MutableBoard board = (b instanceof MutableBoard) ? (MutableBoard) b : new MutableBoard(b);

        Node root = new Node().evaluate(new MutableBoard(b));

        //Sort by score.
        List<MiniMaxAlgorithm.Node> sortedList = root.getChildren().stream()
                .sorted(Comparator.comparing(MiniMaxAlgorithm.Node::getScore).reversed())
                .collect(Collectors.toList());

        MiniMaxAlgorithm.Node best = sortedList.get(0);

        return best.getColumn();
    }

    public class Node {
        private String notes;
        private int depth;
        private int column;
        private int score;
        private IntBinaryOperator operator;

        private Node parent;
        private List<Node> children;

        public Node() {
            this.operator = Integer::max;
            this.score = Integer.MIN_VALUE;
            this.children = new ArrayList<>();
            this.notes = "root";
        }

        public Node(Node parent, int column) {

            this.depth = parent.getDepth() + 1;

            if ((this.depth % 2 == 1)) {
                this.operator = Integer::max;
                this.score = Integer.MIN_VALUE;
                this.notes = "Player " + colors[(parent.getDepth() % 2)] + " max()" ;
            } else {
                this.operator = Integer::min;
                this.score = Integer.MAX_VALUE;
                this.notes = "Player " + colors[(parent.getDepth() % 2)] + " min()" ;
            }

            this.children = new ArrayList<>();
            this.parent = parent;
            this.column = column;

        }

        public Node evaluate(MutableBoard board) {

            boolean gameWon = false;
            int color = colors[depth % 2];

            //Check for a win, is so then no need to recurse any further.
            for (int x = 0, width = board.getWidth(); x < width && !gameWon; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, color);
                    if( gameWon = TurnUtils.getConnections(board).hasWinningLine(board.getGoal()) ){
                        children.add(new Node(this, x).rollUpScore( depth % 2 ==0 ? 77777 : -77777));
                    }
                    board.undo();
                }
            }

            if( gameWon ) return this;

            for (int x = 0, width = board.getWidth(); x < width; x++) {
                if (board.canDropPiece(x)) {
                    int y = board.dropPiece(x, color);
                    Node node = new Node(this, x);

                    //System.out.printf("%d : [%d,%d]%n", node.getDepth(), x, y);

                    children.add(node);

                    if (depth >= maxDepth) {
                        int score = scoreStrategy.scoreMove(board, board.getLastMove(), opponent);
                        node.rollUpScore(score);
                    } else {
                        node.evaluate(board);
                    }
                    board.undo();
                }
            }

            return this;
        }

        public Node rollUpScore(int score) {
            int newScore = operator.applyAsInt(this.score, score);
            //If the score changed, then roll it on up.
            if (newScore != this.score) {
                this.score = newScore;
                if (parent != null) {
                    parent.rollUpScore(newScore);
                }
            }
            return this;
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
            return "Node{" + "depth=" + depth + ", column=" + column + ", score=" + score + ", notes=(" + notes + ")}";
        }
    }
}