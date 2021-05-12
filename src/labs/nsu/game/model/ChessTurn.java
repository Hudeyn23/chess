package labs.nsu.game.model;

import java.util.Objects;

public class ChessTurn {
    private final Cell start;
    private final Cell dest;
    private final ChessBoard board;
    private  String string = null;

    public ChessTurn(Cell start, Cell dest, ChessBoard board) {
        this.start = start;
        this.dest = dest;
        this.board = board;
    }

    public Cell getStart() {
        return start;
    }

    public void makeTurn() {
        string = getString();
        start.getCellFigure().makeTurn(start, dest, board);
    }


    public boolean checkTurn() {
        if (start.isEmpty()) {
            return false;
        }
        return start.getCellFigure().checkTurn(start, dest, board);
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

    public Cell getDest() {
        return dest;
    }

    private String getString() {
        StringBuilder turn = new StringBuilder();
        turn.append(getFigure());
        if (dest.getCellFigure() != null) {
            turn.append("x");
        }
        turn.append(dest.toString());
        return new String(turn);
    }

    @Override
    public String toString() {
        return string;
    }

    private String getFigure() {
        return start.getCellFigure().getFigureSymbol();
    }

}
