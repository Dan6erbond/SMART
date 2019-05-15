import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Builder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(Builder.class.getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), 12);

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setResizable(false);
        SceneManager.getInstance().setStage(primaryStage);

        SceneManager.getInstance().changeScene("/fxml/LangBuilder.fxml");
        primaryStage.show();
    }
}