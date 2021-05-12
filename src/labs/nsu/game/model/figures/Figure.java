package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public interface Figure {
    default void makeTurn(Cell start, Cell dest, ChessBoard board) {
        dest.setCellFigure(this);
        start.setCellFigure(null);
    }

    boolean checkTurn(Cell start, Cell dest, ChessBoard board);

    String getFigureSymbol();

    boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board);

    boolean canCellBeReached(Cell start, Cell dest, ChessBoard board);

    String getStyleClass();

    Color getColor();
}
