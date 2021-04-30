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
        primaryStage.setScene( scene );
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
