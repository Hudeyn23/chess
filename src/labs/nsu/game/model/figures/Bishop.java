package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class Bishop extends AbstractChessFigure {
    public Bishop(Color color) {
        super(color);
    }


    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        return checkDiagonalTurn(start, dest, board);
    }

    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        return canCellBeReached(start,dest,board);
    }

    @Override
    public String getFigureSymbol() {
        return "B";
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        return canCellBeDiagonalReached(start,dest);
    }

    @Override
    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenBishop" : "goldenBishop";
    }
}
