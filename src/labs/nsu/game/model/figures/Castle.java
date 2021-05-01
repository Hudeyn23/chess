package labs.nsu.game.model.figures;

import javafx.scene.paint.Color;
import labs.nsu.game.model.Cell;
import labs.nsu.game.model.ChessBoard;

public class Castle extends AbstractChessFigure {
    public Castle(Color color) {
        super(color);
    }



    @Override
    public boolean checkTurn(Cell start, Cell dest, ChessBoard board) {
        return checkForwardTurn(start,dest,board);

    }

    @Override
    public boolean canCellBeAttacked(Cell start, Cell dest, ChessBoard board) {
        return canCellBeReached(start,dest,board);
    }

    @Override
    public boolean canCellBeReached(Cell start, Cell dest, ChessBoard board) {
        return canCellBeForwardReached(start,dest,board);
    }
    public String getStyleClass() {
        return color.equals(Color.BLACK) ? "greenCastle" : "goldenCastle";
    }
}
