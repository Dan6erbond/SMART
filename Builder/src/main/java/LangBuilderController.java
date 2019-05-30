import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LangBuilderController implements Initializable {

    public TextField folderPath;
    public ListView<StaxelObject> listView;
    public TextField name;
    public TextArea description;
    public Button writeFileBtn;
    public Label done;
    public Label message;
    public TextField code;

    private String lang;
    private File langFile;

    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(param -> new ListCell<StaxelObject>() {
            @Override
            protected void updateItem(StaxelObject object, boolean empty) {
                super.updateItem(object, empty);

                if (empty || object == null) {
                    setText(null);
                } else {
                    setText(object.getCode());
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(event -> {
            StaxelObject object = listView.getSelectionModel().getSelectedItem();
            if (object != null){
                name.setText(object.getName());
                name.setDisable(false);
                description.setText(object.getDescription());
                description.setDisable(false);
                code.setText(object.getCode());
                code.setDisable(false);
            } else {
                name.setDisable(true);
                description.setDisable(true);
                code.setDisable(true);
                name.setText("");
                description.setText("");
                code.setText("");
            }
        });

        writeFileBtn.setDisable(true);
        done.setDisable(true);
        name.setDisable(true);
        code.setDisable(true);
        description.setDisable(true);
        message.setText("");

        code.textProperty().addListener(event -> {
            StaxelObject object = listView.getSelectionModel().getSelectedItem();
            if (object == null) {
                return;
            }
            object.setCode(code.getText());
            listView.refresh();
            try {
                object.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        name.textProperty().addListener(event -> {
            StaxelObject object = listView.getSelectionModel().getSelectedItem();
            if (object == null) {
                return;
            }
            object.setName(name.getText());
        });

        description.addEventFilter(KeyEvent.ANY, event -> {
            if (event.getCode() == KeyCode.ENTER){
                event.consume();
            }
        });

        description.textProperty().addListener(event -> {
            StaxelObject object = listView.getSelectionModel().getSelectedItem();
            if (object == null) {
                return;
            }
            object.setDescription(description.getText());
        });
    }

    public void selectModFolder(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Mod Folder");
        String currentDir = folderPath.getText().isEmpty() ? System.getProperty("user.dir") : folderPath.getText();
        File defaultDirectory = new File(currentDir);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (selectedDirectory == null) {
            return;
        }

        writeFileBtn.setDisable(false);
        folderPath.setText(selectedDirectory.toString());

        langFile = new File(selectedDirectory.getPath(), selectedDirectory.getName() + ".lang");
        ArrayList<File> langFiles = FileHandler.getFiles(".lang", selectedDirectory, false);
        if (langFiles.size() > 0){
            langFile = langFiles.get(0);
        }

        if (langFile.exists()) {
            lang = FileHandler.readFile(langFile);
            message.setText("Lang file located!");
        } else {
            message.setText("No Lang file found. A new one will be created.");
        }

        listView.getItems().clear();

        ArrayList<Tile> tiles = new ArrayList<>();
        for (File file : FileHandler.getFiles(".tile", selectedDirectory, true)){
            tiles.add(new Tile(file, lang));
        }

        ArrayList<Item> items = new ArrayList<>();
        for (File file : FileHandler.getFiles(".item", selectedDirectory, true)){
            items.add(new Item(file, lang));
        }

        if (tiles.size() == 0 && items.size() == 0){
            message.setText("No Tile files found!");
            writeFileBtn.setDisable(true);
        }

        listView.getItems().addAll(tiles);
        listView.getItems().addAll(items);

        done.setDisable(true);
    }

    public void writeLangFile(ActionEvent actionEvent) throws IOException {
        ArrayList<String> langLines = new ArrayList<>();
        langLines.add("language.code=en-GB\nlanguage=English");

        for (StaxelObject object : listView.getItems()) {
            langLines.add(object.getLang());
        }

        FileHandler.writeFile(String.join("\n\n", langLines), langFile);

        done.setDisable(false);
        PauseTransition transition = new PauseTransition(Duration.seconds(1.25));
        transition.setOnFinished(event -> {
            done.setDisable(true);
        });
        transition.play();
    }
}
