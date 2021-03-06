package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class Queen extends AbstractChessFigure {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        return checkDiagonalTurn(start, dest, board) || checkForwardTurn(start, dest, board);
    }

    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        return canCellBeReached(start, dest, board);
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        return canCellBeDiagonalReached(start, dest) || canCellBeForwardReached(start, dest);
    }

    @Override
    public String getFigureSymbol() {
        return "Q";
    }

    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenQueen" : "goldenQueen";
    }

}
