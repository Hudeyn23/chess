package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class Pawn extends AbstractChessFigure {


    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        return dest.isEmpty() && canCellBeReached(start, dest, board) ||
                canCellBeAttacked(start, dest, board) && !dest.isEmpty() && dest.getCellFigure().getColor() != color;

    }

    @Override
    public void makeTurn(Cell start, Cell dest, ChessBoard board) {
        if (isPromotion(start, dest, board)) {
            makePromotion(start, dest, board);
        } else {
            dest.setCellFigure(this);
            start.setCellFigure(null);
        }
    }


    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        if (color == Color.WHITE) {
            return (start.getX() == dest.getX() + 1 || start.getX() == dest.getX() - 1) && start.getY() + 1 == dest.getY();

        } else {
            return (start.getX() == dest.getX() + 1 || start.getX() == dest.getX() - 1) && start.getY() - 1 == dest.getY();
        }
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        if (color == Color.WHITE) {
            return start.getX() == dest.getX() && (start.getY() + 1 == dest.getY() || start.getY() == 1 && start.getY() + 2 == dest.getY());
        } else {
            return start.getX() == dest.getX() && (start.getY() - 1 == dest.getY() || start.getY() == 6 && start.getY() - 2 == dest.getY());
        }
    }


    private boolean isPromotion(Cell start, Cell dest, ChessBoard board) {
        return color == Color.WHITE ? dest.getY() == 7 : dest.getY() == 0;
    }

    private void makePromotion(Cell start, Cell dest, ChessBoard board) {
        dest.setCellFigure(new Queen(color));
        start.setCellFigure(null);
    }

    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenPawn" : "goldenPawn";
    }
}
