package labs.nsu.game.model;

public class ChessBoard {
    private final Cell[][] board;

    public ChessBoard() {
        board = new Cell[8][8];
    }

    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, Cell cell) {
        board[x][y] = cell;
    }
}
