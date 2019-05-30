import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class SceneManager {

    private static SceneManager instance;

    private Stage stage;
    private Parent root;

    //private Color backgroundColor = Color.rgb(86, 221, 84);
    //private Color accentColor = Color.rgb(218, 30, 111);

    //private Color backgroundColor = Color.rgb(38, 50, 56);
    //private Color accentColor = Color.rgb(30, 184, 127);

    private Color backgroundColor = Color.valueOf("#154d6b");
    private Color accentColor = Color.valueOf("#bec9df");

    private SceneManager() throws IOException {
        File file = new File("config.json");
        if (file.exists()) {
            String content = FileHandler.readFile(file);

            boolean write = false;

            JSONObject object = new JSONObject();
            try {
                object = new JSONObject(content);
            } catch (Exception e){
                object.put("backgroundColor", getHEX(backgroundColor));
                object.put("accentColor", getHEX(accentColor));
                write = true;
            }

            if (object.has("backgroundColor")) {
                try {
                    backgroundColor = Color.valueOf(object.getString("backgroundColor"));
                } catch (Exception e) {
                    object.put("backgroundColor", getHEX(backgroundColor));
                    write = true;
                }
            }
            if (object.has("accentColor")) {
                try {
                    accentColor = Color.valueOf(object.getString("accentColor"));
                } catch (Exception e) {
                    object.put("accentColor", getHEX(accentColor));
                    write = true;
                }
            }

            if (write) {
                FileHandler.writeFile(object.toString(), file);
            }
        } else {
            JSONObject object = new JSONObject();
            object.put("backgroundColor", getHEX(backgroundColor));
            object.put("accentColor", getHEX(accentColor));
            FileHandler.writeFile(object.toString(), file);
        }
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            try {
                instance = new SceneManager();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void changeScene(String fxml) throws IOException {
        changeScene(fxml, "");
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void changeScene(String fxml, String redirect) throws IOException {
        if (stage == null) {
            stage = new Stage();
            stage.setResizable(false);
            stage.show();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        root = loader.load();
        root.setOpacity(0);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        if (redirect.equals("")) {
            stage.setOnHidden(null);
        } else {
            stage.setOnHidden(event -> {
                try {
                    stage = null;
                    SceneManager.getInstance().changeScene(redirect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(root.opacityProperty(), 1)));
        timeline.play();

        setColors();
    }

    public void setBackgroundColor(Color color) throws IOException {
        backgroundColor = color;
        setColors();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setAccentColor(Color color) throws IOException {
        accentColor = color;
        setColors();
    }

    public Color getAccentColor() {
        return accentColor;
    }

    /*private Color getTextColor() {
        double darkness = 1 - (0.299 * accentColor.getRed() + 0.587 * accentColor.getGreen() + 0.114 * accentColor.getBlue()) / 255;
        System.out.println(darkness);
        if (darkness < 0.5) {
            return Color.BLACK; // It's a light color
        } else {
            return Color.WHITE; // It's a dark color
        }
    }

    private Color getTextColorBackground() {
        double darkness = 1 - (0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() + 0.114 * backgroundColor.getBlue()) / 255;
        System.out.println(darkness);
        if (darkness < 0.5) {
            return Color.BLACK; // It's a light color
        } else {
            return Color.WHITE; // It's a dark color
        }
    }*/

    private void setColors() throws IOException {
        File file = new File("config.json");
        JSONObject object = new JSONObject();
        object.put("backgroundColor", getHEX(backgroundColor));
        object.put("accentColor", getHEX(accentColor));
        FileHandler.writeFile(object.toString(), file);

        if (root != null) {
            root.setStyle("-accent-color: " + getHEX(accentColor) + ";" + "-background-color: " + getHEX(backgroundColor) + ";" + "-fx-background-color: -background-color;" /* + "-text-color-background: " + textBackgroundRGB + ";"*/);
        }
    }

    private String getHEX(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

}
