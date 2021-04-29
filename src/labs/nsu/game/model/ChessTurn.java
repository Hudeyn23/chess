package labs.nsu.game.model;

import labs.nsu.game.model.figures.Figure;

import java.util.Objects;

public class ChessTurn {
    private final Cell start;
    private final Cell dest;
    private ChessBoard board;
    private final Figure startFigure;

    public ChessTurn(Cell start, Cell dest, ChessBoard board) {
        this.start = start;
        this.dest = dest;
        this.board = board;
        startFigure = start.getCellFigure();
    }

    public Cell getStart() {
        return start;
    }

    public void makeTurn() {
        start.getCellFigure().makeTurn(start, dest, board);
    }


    public boolean checkTurn() {
        if (start.isEmpty()) {
            return false;
        }
        return startFigure.checkTurn(start, dest, board);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessTurn chessTurn = (ChessTurn) o;
        return start.equals(chessTurn.start) && dest.equals(chessTurn.dest) && board.equals(chessTurn.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, dest, board);
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Cell getDest() {
        return dest;
    }


}
