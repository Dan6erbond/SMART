import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Builder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Font.loadFont(Builder.class.getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}