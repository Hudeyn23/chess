package labs.nsu.game.model;

import javafx.scene.paint.Color;
import labs.nsu.game.model.figures.Figure;

public class Cell {
    private Figure cellFigure;
    private final int x, y;

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
    }

    public boolean isEmpty() {
        return cellFigure == null;
    }

    public void setCellFigure(Figure cellFigure) {
        this.cellFigure = cellFigure;
    }
}
