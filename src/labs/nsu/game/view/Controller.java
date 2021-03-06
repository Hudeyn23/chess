
package labs.nsu.game.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import labs.nsu.game.model.ChessBoard;
import labs.nsu.game.model.GameStatus;
import labs.nsu.game.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Controller {


    @FXML
    Rectangle movingRectangle;
    @FXML
    Rectangle leavingRectangle;

    @FXML
    VBox vbox;

    @FXML
    Pane boardPane; // contains rectangle and rectangleGroup


    @FXML
    Group rectangleGroup;

    //
    // bottom row
    //
    @FXML
    Pane row0;
    @FXML
    Rectangle sq0_0;
    @FXML
    Rectangle sq1_0;
    @FXML
    Rectangle sq2_0;
    @FXML
    Rectangle sq3_0;
    @FXML
    Rectangle sq4_0;
    @FXML
    Rectangle sq5_0;
    @FXML
    Rectangle sq6_0;
    @FXML
    Rectangle sq7_0;

    //
    // 2nd from bottom
    //
    @FXML
    Pane row1;
    @FXML
    Rectangle sq0_1;
    @FXML
    Rectangle sq1_1;
    @FXML
    Rectangle sq2_1;
    @FXML
    Rectangle sq3_1;
    @FXML
    Rectangle sq4_1;
    @FXML
    Rectangle sq5_1;
    @FXML
    Rectangle sq6_1;
    @FXML
    Rectangle sq7_1;

    //
    // 3rd from bottom
    //
    @FXML
    Pane row2;
    @FXML
    Rectangle sq0_2;
    @FXML
    Rectangle sq1_2;
    @FXML
    Rectangle sq2_2;
    @FXML
    Rectangle sq3_2;
    @FXML
    Rectangle sq4_2;
    @FXML
    Rectangle sq5_2;
    @FXML
    Rectangle sq6_2;
    @FXML
    Rectangle sq7_2;

    //
    // 4th from bottom
    //
    @FXML
    Pane row3;
    @FXML
    Rectangle sq0_3;
    @FXML
    Rectangle sq1_3;
    @FXML
    Rectangle sq2_3;
    @FXML
    Rectangle sq3_3;
    @FXML
    Rectangle sq4_3;
    @FXML
    Rectangle sq5_3;
    @FXML
    Rectangle sq6_3;
    @FXML
    Rectangle sq7_3;

    //
    // 4th from top
    //
    @FXML
    Pane row4;
    @FXML
    Rectangle sq0_4;
    @FXML
    Rectangle sq1_4;
    @FXML
    Rectangle sq2_4;
    @FXML
    Rectangle sq3_4;
    @FXML
    Rectangle sq4_4;
    @FXML
    Rectangle sq5_4;
    @FXML
    Rectangle sq6_4;
    @FXML
    Rectangle sq7_4;

    //
    // 3rd from top
    //
    @FXML
    Pane row5;
    @FXML
    Rectangle sq0_5;
    @FXML
    Rectangle sq1_5;
    @FXML
    Rectangle sq2_5;
    @FXML
    Rectangle sq3_5;
    @FXML
    Rectangle sq4_5;
    @FXML
    Rectangle sq5_5;
    @FXML
    Rectangle sq6_5;
    @FXML
    Rectangle sq7_5;

    //
    // 2nd from top
    //
    @FXML
    Pane row6;
    @FXML
    Rectangle sq0_6;
    @FXML
    Rectangle sq1_6;
    @FXML
    Rectangle sq2_6;
    @FXML
    Rectangle sq3_6;
    @FXML
    Rectangle sq4_6;
    @FXML
    Rectangle sq5_6;
    @FXML
    Rectangle sq6_6;
    @FXML
    Rectangle sq7_6;

    //
    // top row
    //
    @FXML
    Pane row7;
    @FXML
    Rectangle sq0_7;
    @FXML
    Rectangle sq1_7;
    @FXML
    Rectangle sq2_7;
    @FXML
    Rectangle sq3_7;
    @FXML
    Rectangle sq4_7;
    @FXML
    Rectangle sq5_7;
    @FXML
    Rectangle sq6_7;
    @FXML
    Rectangle sq7_7;
    @FXML
    private Button restart;
    @FXML
    private TextArea textEditor;
    private int lineCount = 0;
    private final List<Pane> panes = new ArrayList<>();
    Model model;

    @FXML
    public void initialize() {
        textEditor.setDisable(true);
        textEditor.setWrapText(true);
        leavingRectangle = new Rectangle(100, 100);
        model = new Model();
        newBoard();
        panes.add(row0);
        panes.add(row1);
        panes.add(row2);
        panes.add(row3);
        panes.add(row4);
        panes.add(row5);
        panes.add(row6);
        panes.add(row7);
        boardPane.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
        boardPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);

        vbox.setMaxWidth(800.0d);
    }

    private Point2D offset = new Point2D(0.0d, 0.0d);
    private boolean movingPiece = false;

    public void checkReleaseOutOfBoard(MouseEvent evt) {
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
        if (!inBoard(mousePoint_s)) {
            leaveBoard(evt);
            evt.consume();
        }
    }

    public void leaveBoard(MouseEvent evt) {
        if (movingPiece) {

            final Timeline timeline = new Timeline();

            offset = new Point2D(0.0d, 0.0d);
            movingPiece = false;

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(movingRectangle.layoutXProperty(), leavingRectangle.getLayoutX()),
                            new KeyValue(movingRectangle.layoutYProperty(), leavingRectangle.getLayoutY()),
                            new KeyValue(movingRectangle.opacityProperty(), 1.0d),
                            new KeyValue(leavingRectangle.opacityProperty(), 0.0d)
                    )
            );
            timeline.play();
        }
    }

    @FXML
    public void startMovingPiece(MouseEvent evt) {
        PickResult result = evt.getPickResult();
        movingRectangle = (Rectangle) result.getIntersectedNode();
        double x = movingRectangle.getLayoutX();
        double y = movingRectangle.getLayoutY();
        leavingRectangle.setLayoutX(x);
        leavingRectangle.setLayoutY(y);
        offset = new Point2D(evt.getX(), evt.getY());
        leavingRectangle.setOpacity(1.0d);
        movingPiece = true;
    }

    @FXML
    public void movePiece(MouseEvent evt) {

        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());

        if (!inBoard(mousePoint_s)) {
            return;
        }

        Point2D mousePoint_p = movingRectangle.localToParent(mousePoint);
        movingRectangle.relocate(mousePoint_p.getX() - offset.getX(), mousePoint_p.getY() - offset.getY());
    }

    private boolean inBoard(Point2D pt) {
        Point2D panePt = boardPane.sceneToLocal(pt);
        return panePt.getX() - offset.getX() >= 0.0d
                && panePt.getY() - offset.getY() >= 0.0d
                && panePt.getX() <= boardPane.getWidth()
                && panePt.getY() <= boardPane.getHeight();
    }

    @FXML
    public void finishMovingPiece(MouseEvent evt) {
        offset = new Point2D(0.0d, 0.0d);
        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePointScene = movingRectangle.localToScene(mousePoint);

        Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        if (r != null) {

            Point2D rectScene = r.localToScene(r.getX(), r.getY());
            Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());
            if (model.makeTurn((int) leavingRectangle.getLayoutX() / 100, (int) (7 - leavingRectangle.getLayoutY() / 100), (int) parent.getX() / 100, (int) (7 - parent.getY() / 100))) {
                removeRectangle(parent.getX(), parent.getY());
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(movingRectangle.layoutXProperty(), parent.getX()),
                                new KeyValue(movingRectangle.layoutYProperty(), parent.getY()),
                                new KeyValue(leavingRectangle.opacityProperty(), 0.0d)
                        )
                );
                writeTurn();

            } else {

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(movingRectangle.layoutXProperty(), leavingRectangle.getLayoutX()),
                                new KeyValue(movingRectangle.layoutYProperty(), leavingRectangle.getLayoutY()),
                                new KeyValue(leavingRectangle.opacityProperty(), 0.0d)
                        )
                );
            }
        }
        timeline.play();
        timeline.setOnFinished(this::checkGameStatus);
        movingPiece = false;
    }


    private void checkGameStatus(ActionEvent actionEvent) {
        if (model.getGameStatus() == GameStatus.GAME) return;
        String message;
        if (model.getGameStatus() == GameStatus.WHITE_WIN) {
            message = "Congratulations, White win";
        } else if (model.getGameStatus() == GameStatus.BLACK_WIN) {
            message = "Congratulations, Black win";
        } else {
            message = "There was a stalemate in the game. Draw";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End Game");
        alert.setHeaderText("Hey, it`s game end");
        alert.setContentText(message);
        alert.show();

    }


    private void removeRectangle(double layoutX, double layoutY) {
        boardPane.getChildren().removeIf(node -> node.getLayoutX() == layoutX && node.getLayoutY() == layoutY && node.getId() == null);
    }

    private Rectangle pickRectangle(MouseEvent evt) {
        return pickRectangle(evt.getSceneX(), evt.getSceneY());
    }

    private Rectangle pickRectangle(double sceneX, double sceneY) {
        Rectangle pickedRectangle = null;
        for (Pane row : panes) {
            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mpLocal = row.sceneToLocal(mousePoint);

            if (row.contains(mpLocal)) {

                for (Node cell : row.getChildrenUnmodifiable()) {

                    Point2D mpLocalCell = cell.sceneToLocal(mousePoint);

                    if (cell.contains(mpLocalCell)) {
                        pickedRectangle = (Rectangle) cell;
                        break;
                    }
                }
                break;
            }
        }
        return pickedRectangle;
    }

    @FXML
    void restart(ActionEvent event) {
        textEditor.appendText("kavo");
        clearBoard();
        newBoard();
        textEditor.clear();

    }

    private void newBoard() {
        model = new Model();
        ChessBoard board = model.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board.getCell(j, i).isEmpty()) {
                    Rectangle figure = new Rectangle(100, 100);
                    figure.setLayoutX(j * 100);
                    figure.setLayoutY(700 - i * 100);
                    figure.setFill(Color.DODGERBLUE);
                    figure.setStroke(Color.TRANSPARENT);
                    figure.getStyleClass().add(board.getCell(j, i).getCellFigure().getStyleClass());
                    figure.setOnMouseDragged(this::movePiece);
                    figure.setOnMousePressed(this::startMovingPiece);
                    figure.setOnMouseReleased(this::finishMovingPiece);
                    boardPane.getChildren().add(figure);
                }
            }
        }
    }

    private void clearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                removeRectangle(j * 100, 700 - i * 100);

            }
        }
    }

    private void writeTurn() {
       if(lineCount == 2){
            textEditor.appendText(System.getProperty("line.separator"));
            textEditor.appendText(model.getLastTurn() + " ");
            lineCount = 1;
        } else {
            textEditor.appendText(model.getLastTurn()+" ");
            lineCount++;
        }
    }
}
