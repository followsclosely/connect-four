package net.wilson.games.connect.ai;

import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxAlgorithm {

    private int color;
    private int[] colors;
    private int width;
    private int maxDepth = 6;
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

        private List<Node> children = null;

        public Node() {
            this.children = new ArrayList<>(board.getWidth());
        }

        public Node(int column, int depth, int score) {
            this();
            this.depth = depth;
            this.column = column;
            this.score = score;
        }

        public void evaluate(){

            //If the game is already won, then do not dig down any further.
            if( board.getWinningConnections().isEmpty())
            {
                int color = colors[depth%2];
                for (int x = 0; x < width; x++) {
                    int y = board.dropPiece(x, color);
                    if (y != -1) {
                        Node node = new Node(x, depth + 1, scoreStrategy.scoreMove(board, x, y));
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
            return "Node{" +
                    "depth=" + depth +
                    ", column=" + column +
                    ", score=" + score +
                    '}';
        }
    }
}