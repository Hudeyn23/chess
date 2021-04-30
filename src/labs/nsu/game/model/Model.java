package labs.nsu.game.model;

import javafx.scene.paint.Color;
import labs.nsu.game.model.figures.*;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private ChessBoard board;
    private Cell whiteKing;
    private Cell blackKing;
    private List<Cell> pins;
    private List<Check> checks;
    private Color currentTurn = Color.WHITE;
    private boolean isCheck = false;
    private List<Cell> kingPossibleTurns;
    private List<Cell> possibleDests;

    public Model() {
        pins = new ArrayList<>();
        checks = new ArrayList<>();
        board = new ChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color cellColor = i + j % 2 == 0 ? Color.BLACK : Color.WHITE;
                board.setCell(i, j, new Cell(cellColor, null, i, j));
            }
        }
        for (int i = 0; i < 8; i = i + 7) {
            Color figureColor = i % 2 == 0 ? Color.WHITE : Color.BLACK;
            board.getCell(0, i).setCellFigure(new Castle(figureColor));
            board.getCell(1, i).setCellFigure(new Knight(figureColor));
            board.getCell(2, i).setCellFigure(new Bishop(figureColor));
            if (figureColor.equals(Color.WHITE)) {
                Figure king = new King(Color.WHITE);
                (whiteKing = board.getCell(3, i)).setCellFigure(king);
            } else {
                Figure king = new King(Color.BLACK);
                (blackKing = board.getCell(3, i)).setCellFigure(king);
            }
            board.getCell(4, i).setCellFigure(new Queen(figureColor));
            board.getCell(5, i).setCellFigure(new Bishop(figureColor));
            board.getCell(6, i).setCellFigure(new Knight(figureColor));
            board.getCell(7, i).setCellFigure(new Castle(figureColor));
        }
        for (int i = 0; i < 8; i++) {
            board.getCell(i, 1).setCellFigure(new Pawn(Color.WHITE));
            board.getCell(i, 6).setCellFigure(new Pawn(Color.BLACK));
        }
    }

    public ChessBoard getBoard() {
        return board;
    }

    public boolean makeTurn(int startX, int startY, int destX, int destY) {
        Cell currentTurnKing = currentTurn == Color.BLACK ? blackKing : whiteKing;
        ChessTurn turn = new ChessTurn(board.getCell(startX, startY), board.getCell(destX, destY), board);
        if (isMate()) {
            // конец игры
        }
        if (!turn.checkTurn()) {
            return false;
        }
        if (turn.getStart().getCellFigure().getColor() != currentTurn) {
            return false;
        }
        if (checks.isEmpty()) {
            if (pins.contains(turn.getStart())) {
                return false;
            }
            if (turn.getStart().getCellFigure() instanceof King) {
                changeKing(turn.getStart(),turn.getDest());
            }
            turn.makeTurn();
            changeTurn();
            // обновить view
            return true;
        } else if (checks.size() >= 2) {
            if (currentTurnKing.getX() == startX && currentTurnKing.getY() == startY) {
                if (turn.getStart().getCellFigure() instanceof King) {
                    changeKing(turn.getStart(),turn.getDest());
                }
                turn.makeTurn();
                // обновить view
            }
            return false;
        } else {
            if (possibleDests.contains(turn.getDest())) {
                if (turn.getStart().getCellFigure() instanceof King) {
                    changeKing(turn.getStart(),turn.getDest());
                }
                changeTurn();
                turn.makeTurn();
                // обновить view
            } else {
                return false;
            }
        }
        return false;
    }


    private void getChecksAndPins(Cell kingCell) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        Cell possiblePin = null;
        for (int[] direction : directions) {
            for (int x = kingCell.getX() + direction[0], y = kingCell.getY() + direction[1]; ; x += direction[0], y += direction[1]) {
                if (x > 7 || x < 0 || y > 7 || y < 0) break;
                if ((!board.getCell(x, y).isEmpty()) && board.getCell(x, y).getCellFigure().getColor() == kingCell.getCellFigure().getColor()) {
                    if (possiblePin != null) {
                        break;
                    }
                    possiblePin = board.getCell(x, y);
                }
                if (!board.getCell(x, y).isEmpty() && board.getCell(x, y).getCellFigure().getColor() != kingCell.getCellFigure().getColor()) {
                    if (board.getCell(x, y).getCellFigure().canCellBeAttacked(board.getCell(x, y), kingCell, board)) {
                        if (possiblePin == null) {
                            checks.add(new Check(board.getCell(x, y), direction));
                        } else {
                            pins.add(possiblePin);
                        }
                    }
                    break;
                }
            }
            possiblePin = null;
        }
        int[][] possibleKnightPositions = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        for (int[] positions : possibleKnightPositions) {
            int x = kingCell.getX() + positions[0];
            int y = kingCell.getY() + positions[1];
            if (x > 7 || x < 0 || y > 7 || y < 0) continue;
            if (board.getCell(x, y).getCellFigure() instanceof Knight && board.getCell(x, y).getCellFigure().getColor() != kingCell.getCellFigure().getColor()) {
                checks.add(new Check(board.getCell(x, y), positions));
            }
        }
    }

    private List<Cell> getKingPossibleTurns(Cell king) {
        int[][] turns = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        List<Cell> possibleTurns = new ArrayList<>();
        for (int[] turn : turns) {
            int x = king.getX() + turn[0], y = king.getY() + turn[1];
            if (x > 7 || x < 0 || y > 7 || y < 0) break;
            if ((king.getCellFigure().checkTurn(king, board.getCell(x, y), board))) {
                possibleTurns.add(board.getCell(x, y));
            }
        }
        return possibleTurns;
    }

    private boolean canSomeFigureReachCell(Cell dest, Color color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (!board.getCell(x, y).isEmpty() && board.getCell(x, y).getCellFigure().getColor() == color)
                    if (board.getCell(x, y).getCellFigure().checkTurn(board.getCell(x, y), dest, board))
                        return true;
            }
        }
        return false;
    }

    private List<Cell> getPossibleDestCells(Cell kingCell, Check check) {
        int[] direction = check.getDirection();
        List<Cell> possibleDestCells = new ArrayList<>();
        for (int x = kingCell.getX() + direction[0], y = kingCell.getY() + direction[1]; ; x += direction[0], y += direction[1]) {
            if (x > 7 || x < 0 || y > 7 || y < 0) break;
            if (x == check.getAttackCell().getX() && y == check.getAttackCell().getY()) {
                if (canSomeFigureReachCell(board.getCell(x, y), currentTurn))
                    possibleDestCells.add(board.getCell(x, y));
                return possibleDestCells;
            } else {
                if (canSomeFigureReachCell(board.getCell(x, y), currentTurn))
                    possibleDestCells.add(board.getCell(x, y));
            }
        }
        return possibleDestCells;
    }

    private boolean isMate() {
        Cell king = currentTurn == Color.BLACK ? blackKing : whiteKing;
        getChecksAndPins(king);
        if (checks.isEmpty()) {
            return false;
        }
        possibleDests = getPossibleDestCells(king, checks.get(0));
        kingPossibleTurns = getKingPossibleTurns(king);
        if (checks.size() == 1) {
            return possibleDests.isEmpty() && kingPossibleTurns.isEmpty();
        } else {
            return kingPossibleTurns.isEmpty();
        }
    }

    private void changeTurn() {
        currentTurn = currentTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private void changeKing(Cell start,Cell dest){
        if (currentTurn == Color.WHITE) {
            whiteKing = dest;
        } else {
            blackKing = dest;
        }
    }

}


