package labs.nsu.game.model;

import labs.nsu.game.model.figures.Figure;

public class Cell {
    private Figure cellFigure;
    private final int x, y;
    private final String cell;

    public Figure getCellFigure() {
        return cellFigure;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell(Figure cellFigure, int x, int y) {
        this.cellFigure = cellFigure;
        this.x = x;
        this.y = y;
        char first = (char) ('a' + x + 1);
        char second = (char) ('0' + y + 1);
        cell = "" + first + second;
    }

    public boolean isEmpty() {
        return cellFigure == null;
    }

    public void setCellFigure(Figure cellFigure) {
        this.cellFigure = cellFigure;
    }

    @Override
    public String toString() {
        return cell;
    }
}
