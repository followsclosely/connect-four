package io.github.followsclosley.connect.swing;


import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This panel draws the connect four board given a Board
 *
 * @see Board
 */
public class BoardPanel extends JPanel {

    public static final int PIECE_SIZE = 75;

    protected Dimension defaultDimension;

    private Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};

    private Board board;
    private Turn turn;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE - 5, board.getHeight() * PIECE_SIZE - 5);
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the pieces on the board
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                g.setColor(COLORS[board.getPiece(x, y) + 1]);
                g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE-5, PIECE_SIZE-5, PIECE_SIZE-5, PIECE_SIZE-5);
            }
        }

        //Draw the winning lines
        if (board.getTurns().size() > 0) {
            Turn turn = TurnUtils.getConnections(board);
            if (turn.hasWinningLine(board.getGoal()))
                for (Turn.Line line : turn.getLines()) {
                    if (line.getPieceCount() >= board.getGoal()) {
                        List<Coordinate> coordinates = line.getConnected();
                        Coordinate start = coordinates.get(0);
                        Coordinate end = coordinates.get(coordinates.size() - 1);

                        ((Graphics2D) g).setStroke(new BasicStroke(10));
                        g.setColor(Color.YELLOW);
                        g.drawLine(start.getX() * PIECE_SIZE + (PIECE_SIZE/2), start.getY() * PIECE_SIZE + (PIECE_SIZE/2), end.getX() * PIECE_SIZE + (PIECE_SIZE/2), end.getY() * PIECE_SIZE + (PIECE_SIZE/2));
                    }
                }
        }
    }
}