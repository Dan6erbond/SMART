import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Builder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(Builder.class.getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), 12);

        primaryStage.setResizable(false);
        SceneManager.getInstance().setStage(primaryStage);

        SceneManager.getInstance().changeScene("/fxml/Home.fxml");
        primaryStage.show();
    }
}