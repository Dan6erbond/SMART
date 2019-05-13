import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openLangBuilder(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        openScene(stage, "/fxml/LangBuilder.fxml");
    }

    public void openModBuilder(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        openScene(stage, "/fxml/ModBuilder.fxml");
    }

    private void openScene(Stage stage, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Parent root = loader.load();
        root.setOpacity(0);

        stage.setScene(new Scene(root));

        stage.setOnHidden(event -> {
            try{
                FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));

                Parent r = l.load();
                r.setOpacity(0);

                Stage s = new Stage(StageStyle.UTILITY);
                s.setResizable(false);
                s.setScene(new Scene(r));

                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(r.opacityProperty(), 1)));
                timeline.play();

                s.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(root.opacityProperty(), 1)));
        timeline.play();
    }
}
