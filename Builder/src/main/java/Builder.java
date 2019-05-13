import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Builder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(Builder.class.getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), 12);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));

        Parent root = loader.load();
        root.setOpacity(0);
        root.setStyle("-accent-color: white;");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setResizable(false);
        primaryStage.show();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(root.opacityProperty(), 1)));
        timeline.play();
    }
}