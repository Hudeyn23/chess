package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class Knight extends AbstractChessFigure {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        if (!dest.isEmpty() && dest.getCellFigure().getColor() == color) {
            return false;
        }
        return canCellBeReached(start, dest, board);
    }

    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        return canCellBeReached(start, dest, board);
    }
    @Override
    public String getFigureSymbol() {
        return "N";
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        return Math.abs(start.getY() - dest.getY()) == 1 && (Math.abs(start.getX() - dest.getX())) == 2
                || Math.abs(start.getX() - dest.getX()) == 1 && (Math.abs(start.getY() - dest.getY())) == 2;
    }

    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenKnight" : "goldenKnight";
    }
}
