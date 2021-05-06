package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class King extends AbstractChessFigure {
    private boolean isCastlingPossible = true;

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        if (!dest.isEmpty() && dest.getCellFigure().getColor() == color) {
            return false;
        }
        return !isCellUnderAttack(dest, board) && (canCellBeReached(start, dest, board) || castling(start, dest, board));
    }

    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenKing" : "goldenKing";
    }

    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        return canCellBeReached(start, dest, board);
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        return Math.abs(start.getX() - dest.getX()) <= 1 && Math.abs(start.getY() - dest.getY()) <= 1;
    }

    @Override
    public void makeTurn(Cell start, Cell dest, ChessBoard board) {
        if (castling(start, dest, board)) {
            makeCastling(start, dest, board);
        } else {
            dest.setCellFigure(this);
            start.setCellFigure(null);
        }
        isCastlingPossible = false;
    }


    private boolean castling(Cell start, Cell dest, ChessBoard board) {
        if (!isCastlingPossible) {
            return false;
        }
        if ((start.getX() - dest.getX() == 2) && (start.getY() - dest.getY() == 0)) {
            if (board.getCell(0, start.getY()).getCellFigure() instanceof Castle &&
                    board.getCell(0, start.getY()).getCellFigure().getColor().equals(color)) {
                return board.getCell(0, start.getY()).getCellFigure().canCellBeReached(start, dest, board);
            }
        }
        if ((start.getX() - dest.getX() == -2) && (start.getY() - dest.getY() == 0)) {
            if (board.getCell(7, start.getY()).getCellFigure() instanceof Castle &&
                    board.getCell(7, start.getY()).getCellFigure().getColor().equals(color)) {
                return board.getCell(7, start.getY()).getCellFigure().canCellBeReached(start, dest, board);
            }
        }
        return false;
    }

    private void makeCastling(Cell start, Cell dest, ChessBoard board) {
        if ((start.getX() - dest.getX() == 2) && (start.getY() - dest.getY() == 0)) {
            dest.setCellFigure(this);
            start.setCellFigure(null);
            board.getCell(3, start.getY()).setCellFigure(board.getCell(0, start.getY()).getCellFigure());
            board.getCell(0, start.getY()).setCellFigure(null);
        }
        if ((start.getX() - dest.getX() == -2) && (start.getY() - dest.getY() == 0)) {
            dest.setCellFigure(this);
            start.setCellFigure(null);
            board.getCell(5, start.getY()).setCellFigure(board.getCell(7, start.getY()).getCellFigure());
            board.getCell(7, start.getY()).setCellFigure(null);
        }
    }

    private boolean isCellUnderAttack(Cell cell, ChessBoard board) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        for (int[] direction : directions) {
            for (int x = cell.getX() + direction[0], y = cell.getY() + direction[1]; ; x += direction[0], y += direction[1]) {
                if (x > 7 || x < 0 || y > 7 || y < 0) break;
                if (!board.getCell(x, y).isEmpty() && board.getCell(x, y).getCellFigure().getColor() == color) {
                    break;
                }
                if (!board.getCell(x, y).isEmpty() && board.getCell(x, y).getCellFigure().getColor() != color) {
                    if (board.getCell(x, y).getCellFigure().canCellBeAttacked(board.getCell(x, y), cell, board)) {
                        return true;
                    }
                    break;
                }
            }
        }
        int[][] possibleKnightPositions = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        for (
                int[] positions : possibleKnightPositions) {
            int x = cell.getX() + positions[0];
            int y = cell.getY() + positions[1];
            if (x > 7 || x < 0 || y > 7 || y < 0) continue;
            if (board.getCell(x, y).getCellFigure() instanceof Knight && board.getCell(x, y).getCellFigure().getColor() != color) {
                return true;
            }
        }
        return false;
    }

}
