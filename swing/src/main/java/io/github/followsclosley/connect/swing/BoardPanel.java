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

    protected Dimension defaultDimension;

    private Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};

    private Board board;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * 50 - 5, board.getHeight() * 50 - 5);
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
                g.fillRoundRect(x * 50, y * 50, 45, 45, 45, 45);
            }
        }

        //Draw the winning lines
        if( board.getTurns().size() > 0) {
            Coordinate turn = board.getTurns().get(board.getTurns().size() - 1);
            for (Turn.Line line : TurnUtils.getWinningConnections(board, turn, board.getPiece(turn.getX(), turn.getY())).getLines()) {
                List<Coordinate> coordinates = line.getCoordinates();
                Coordinate start = coordinates.get(0);
                Coordinate end = coordinates.get(coordinates.size() - 1);

                ((Graphics2D) g).setStroke(new BasicStroke(10));
                g.setColor(Color.YELLOW);
                g.drawLine(start.getX() * 50 + 24, start.getY() * 50 + 24, end.getX() * 50 + 24, end.getY() * 50 + 24);
            }
        }
    }
}