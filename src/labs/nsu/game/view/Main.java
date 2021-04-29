package labs.nsu.game.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/sample.fxml"));

        Parent p = fxmlLoader.load();

        Controller c = fxmlLoader.getController();

        Scene scene = new Scene(p);
        scene.getStylesheets().add("sample.css");
        scene.setOnMouseMoved((evt) -> c.mouseMoved(evt));
        scene.setOnMouseDragged((evt)->c.mouseMoved(evt));
/*		scene.setOnMousePressed((evt) -> c.mousePressed(evt));
		scene.setOnMouseReleased((evt) -> c.mouseReleased(evt));
*/
        primaryStage.setScene( scene );
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
