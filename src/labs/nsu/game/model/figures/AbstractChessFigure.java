package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

abstract class AbstractChessFigure implements Figure {
    protected final Color color;

    public AbstractChessFigure(Color color) {
        this.color = color;
    }


    @Override
    public Color getColor() {
        return color;
    }


    protected boolean checkDiagonalTurn(Cell start, Cell dest, ChessBoard board) {
        if (Math.abs(start.getY() - dest.getY()) == Math.abs(start.getX() - dest.getX()) && Math.abs(start.getY() - dest.getY()) != 0) {
            Cell minX = start.getX() < dest.getX() ? start : dest;
            Cell maxX = start.getX() < dest.getX() ? dest : start;
            if (minX.getY() < maxX.getY()) {
                for (int x = minX.getX() + 1, y = minX.getY() + 1;
                     x != maxX.getX() && y != maxX.getY(); x++, y++) {
                    if (!board.getCell(x, y).isEmpty())
                        return false;

                }
                return true;
            }
            if (minX.getY() > maxX.getY()) {
                for (int x = minX.getX() + 1, y = minX.getY() - 1;
                     x != maxX.getX() && y != maxX.getY(); x++, y--) {
                    if (!board.getCell(x, y).isEmpty())
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    protected boolean checkForwardTurn(Cell start, Cell dest, ChessBoard board) {
        if (start.getX() == dest.getX() && start.getY() != dest.getY()) {
            int min = Math.min(start.getY(), dest.getY());
            int max = Math.max(start.getY(), dest.getY());
            for (int i = min + 1; i < max - 1; i++) {
                if (!board.getCell(start.getX(), i).isEmpty())
                    return false;
            }
            return true;
        }
        if (start.getY() == dest.getY() && start.getX() != dest.getX()) {
            int min = Math.min(start.getX(), dest.getX());
            int max = Math.max(start.getX(), dest.getX());
            for (int i = min + 1; i < max - 1; i++) {
                if (!board.getCell(i, start.getY()).isEmpty())
                    return false;
            }
            return true;
        }
        return false;

    }

    protected boolean canCellBeDiagonalReached(Cell start, Cell dest) {
        return Math.abs(start.getX() - dest.getX()) == Math.abs(start.getY() - dest.getY());
    }

    protected boolean canCellBeForwardReached(Cell start, Cell dest) {
        return start.getX() == dest.getX() || start.getY() == dest.getY();
    }


}
