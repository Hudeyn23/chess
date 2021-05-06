package labs.nsu.game.model;

import javafx.scene.paint.Color;
import labs.nsu.game.model.figures.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private final ChessBoard board;
    private Cell whiteKing;
    private Cell blackKing;
    private final Map<Cell, List<int[]>> pins;
    private final List<Check> checks;
    private Color currentTurn;
    private List<Cell> possibleDests;
    private boolean newTurn = true;
    private GameStatus status;

    public Model() {
        currentTurn = Color.WHITE;
        status = GameStatus.GAME;
        pins = new HashMap<>();
        checks = new ArrayList<>();
        board = new ChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color cellColor = i + j % 2 == 0 ? Color.BLACK : Color.WHITE;
                board.setCell(i, j, new Cell(null, i, j));
            }
        }
        for (int i = 0; i < 8; i = i + 7) {
            Color figureColor = i % 2 == 0 ? Color.WHITE : Color.BLACK;
            board.getCell(0, i).setCellFigure(new Castle(figureColor));
            board.getCell(1, i).setCellFigure(new Knight(figureColor));
            board.getCell(2, i).setCellFigure(new Bishop(figureColor));
            board.getCell(3, i).setCellFigure(new Queen(figureColor));
            if (figureColor.equals(Color.WHITE)) {
                Figure king = new King(Color.WHITE);
                (whiteKing = board.getCell(4, i)).setCellFigure(king);
            } else {
                Figure king = new King(Color.BLACK);
                (blackKing = board.getCell(4, i)).setCellFigure(king);
            }
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
        if (!turn.checkTurn() || turn.getStart().getCellFigure().getColor() != currentTurn) {
            newTurn = false;
            return false;
        }
        if (checks.isEmpty()) {
            if (pins.containsKey(turn.getStart())) {
                if (isPinTurnPossible(turn.getStart(), turn.getDest())) {
                    turn.makeTurn();
                    changeTurn();
                    return true;
                }
                newTurn = false;
                return false;
            }
            if (turn.getStart().getCellFigure() instanceof King) {
                changeKing(turn.getStart(), turn.getDest());
            }
            turn.makeTurn();
            changeTurn();
            return true;
        } else if (checks.size() >= 2) {
            if (turn.getStart().getCellFigure() instanceof King) {
                changeKing(turn.getStart(), turn.getDest());
                turn.makeTurn();
                changeTurn();
                return true;
            }
        } else {
            if (currentTurnKing.getX() == startX && currentTurnKing.getY() == startY || possibleDests.contains(turn.getDest())) {
                if (turn.getStart().getCellFigure() instanceof King) {
                    changeKing(turn.getStart(), turn.getDest());
                }
                changeTurn();
                turn.makeTurn();
                return true;
            }

        }
        return false;
    }


    private void getChecksAndPins(Cell kingCell) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        Cell possiblePin = null;
        checks.clear();
        pins.clear();
        for (int[] direction : directions) {
            for (int x = kingCell.getX() + direction[0], y = kingCell.getY() + direction[1]; ; x += direction[0], y += direction[1]) {
                if (x > 7 || x < 0 || y > 7 || y < 0) break;
                if ((!board.getCell(x, y).isEmpty()) && board.getCell(x, y).getCellFigure().getColor() == kingCell.getCellFigure().getColor()) {
                    if (possiblePin != null) {
                        break;
                    }

                    possiblePin = board.getCell(x, y);
                } else if (!board.getCell(x, y).isEmpty() && board.getCell(x, y).getCellFigure().getColor() != kingCell.getCellFigure().getColor()) {
                    if (board.getCell(x, y).getCellFigure().canCellBeAttacked(board.getCell(x, y), kingCell, board)) {
                        if (possiblePin == null) {
                            checks.add(new Check(board.getCell(x, y), direction));
                        } else {
                            pins.computeIfAbsent(possiblePin, (k) -> new ArrayList<>());
                            pins.get(possiblePin).add(direction);
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
        List<Cell> kingPossibleTurns = getKingPossibleTurns(king);
        if (checks.size() == 1) {
            return possibleDests.isEmpty() && kingPossibleTurns.isEmpty();
        } else {
            return kingPossibleTurns.isEmpty();
        }
    }

    private void changeTurn() {
        currentTurn = currentTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
        if (isMate()) {
            status = currentTurn == Color.WHITE ? GameStatus.BLACK_WIN : GameStatus.WHITE_WIN;
        }
        newTurn = true;
    }

    private void changeKing(Cell start, Cell dest) {
        if (currentTurn == Color.WHITE) {
            whiteKing = dest;
        } else {
            blackKing = dest;
        }
    }

    private boolean isPinTurnPossible(Cell pin, Cell dest) {
        if (pins.get(pin).size() <= 1) {
            int[] direction = pins.get(pin).get(0);
            for (int x = pin.getX() + direction[0], y = pin.getY() + direction[1]; ; x += direction[0], y += direction[1]) {
                if (x > 7 || x < 0 || y > 7 || y < 0) break;
                if (x == dest.getX() && y == dest.getY()) return true;
            }
            for (int x = pin.getX() + direction[0] * -1, y = pin.getY() + direction[1] * -1; ; x += direction[0] * -1, y += direction[1] * -1) {
                if (x > 7 || x < 0 || y > 7 || y < 0) break;
                if (x == dest.getX() && y == dest.getY()) return true;
            }
        }
        return false;
    }

    public GameStatus getGameStatus() {
        return status;
    }

}


