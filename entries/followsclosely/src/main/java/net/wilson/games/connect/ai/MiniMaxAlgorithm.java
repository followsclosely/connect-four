package net.wilson.games.connect.ai;

import net.wilson.games.connect.impl.MutableBoard;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class MiniMaxAlgorithm {

    private int color;
    private int[] colors;
    private int width;
    private int maxDepth = 3;
    private MutableBoard board;
    private ScoreStrategy scoreStrategy;

    public MiniMaxAlgorithm(MutableBoard board, int color, int... colors) {
        this.board = board;
        this.width = board.getWidth();
        this.colors = colors;
        this.scoreStrategy = new ScoreStrategy(color);
    }

    public Node evaluate(){
        Node root = new Node();
        root.evaluate();
        return root;
    }

    public class Node {
        private int depth = 0;
        private int column;
        private int score = -1;
        private int minMaxScore = -1;
        private IntBinaryOperator operator;

        private Node parent;
        private List<Node> children = null;

        public Node() {
            this.operator = Math::max;
            this.children = new ArrayList<>(board.getWidth());
        }

        public Node(Node parent, int column, int depth, int score) {
            this();
            this.operator = (depth % 2 == 0) ? Integer::max : Integer::min;
            this.parent = parent;
            this.depth = depth;
            this.column = column;
            this.score = score;
        }

        public void evaluate(){

            //If the game is already won, then do not dig down any further.
            if( !board.getWinningConnections().isEmpty() || depth >= maxDepth) {
                this.minMaxScore = scoreStrategy.scoreMove(board);
                this.parent.childScore(this.minMaxScore);
            } else {
                int color = colors[depth%2];
                for (int x = 0; x < width; x++) {
                    int y = board.dropPiece(x, color);
                    if (y != -1) {
                        Node node = new Node(this, x, depth + 1, scoreStrategy.scoreMove(board));
                        //System.out.println( node.column + " -> " + node.score);
                        children.add(node);

                        if (depth < maxDepth) {
                            node.evaluate();
                        }
                    }
                    board.undo();
                }
            }
        }

        public void childScore(int score){
            this.minMaxScore = operator.applyAsInt(this.minMaxScore, score);
            if( parent != null){
                parent.childScore(this.minMaxScore);
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
            return "Node{" + "depth=" + depth + ", column=" + column + ", score=" + score + "/ " + minMaxScore + '}';
        }
    }
}