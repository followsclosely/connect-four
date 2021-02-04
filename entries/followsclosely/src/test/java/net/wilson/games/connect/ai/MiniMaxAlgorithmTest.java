package net.wilson.games.connect.ai;

import junit.framework.TestCase;
import net.wilson.games.connect.impl.MutableBoard;

public class MiniMaxAlgorithmTest extends TestCase {

    public void testEvaluate() {
        MutableBoard board = new MutableBoard();
        MiniMaxAlgorithm algorithm = new MiniMaxAlgorithm(board, 1, 1,2);

        MiniMaxAlgorithm.Node root = algorithm.evaluate();

        System.out.print(root);
        //printNode(root);
    }

    private void printNode(MiniMaxAlgorithm.Node node){
        System.out.print(node.getDepth() + "]    ");
        for(MiniMaxAlgorithm.Node child : node.getChildren()){
            System.out.print(String.format("%d: %d      ",child.getColumn(), child.getScore()));
        }
        System.out.println();

        for(MiniMaxAlgorithm.Node child : node.getChildren()){
            printNode(child);
        }
    }
}