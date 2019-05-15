import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LangBuilderController implements Initializable {

    public TextField folderPath;
    public ListView<Tile> listView;
    public TextField name;
    public TextArea description;
    public Button writeFileBtn;
    public Label done;

    private String lang;
    private File selectedDirectory;

    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(param -> new ListCell<Tile>() {
            @Override
            protected void updateItem(Tile item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getCode());
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(event -> {
            Tile tile = listView.getSelectionModel().getSelectedItem();
            if (tile != null){
                name.setText(tile.getName());
                description.setText(tile.getDescription());
                name.setDisable(false);
                description.setDisable(false);
            } else {
                name.setDisable(true);
                description.setDisable(true);
                name.setText("");
                description.setText("");
            }
        });

        writeFileBtn.setDisable(true);
        done.setDisable(true);
        name.setDisable(true);
        description.setDisable(true);
    }

    public void selectModFolder(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Mod Folder");
        String currentDir = folderPath.getText().isEmpty() ? System.getProperty("user.dir") : folderPath.getText();
        File defaultDirectory = new File(currentDir);
        chooser.setInitialDirectory(defaultDirectory);
        selectedDirectory = chooser.showDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (selectedDirectory == null) {
            return;
        }

        writeFileBtn.setDisable(false);
        folderPath.setText(selectedDirectory.toString());

        File langFile = new File(selectedDirectory.getPath(), selectedDirectory.getName() + ".lang");
        if (langFile.exists()) {
            try {
                lang = FileHandler.readFile(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listView.getItems().clear();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (File file : FileHandler.getFiles(".tile", selectedDirectory, true)){
            tiles.add(new Tile(file, lang));
        }
        listView.getItems().addAll(tiles);

        done.setDisable(true);
    }

    public void writeLangFile(ActionEvent actionEvent) throws IOException {
        ArrayList<String> langLines = new ArrayList<>();
        langLines.add("language.code=en-GB\nlanguage=English");

        for (Tile tile : listView.getItems()) {
            langLines.add(tile.getLang());
        }

        File langFile = new File(selectedDirectory.getPath(), selectedDirectory.getName() + ".lang");
        FileHandler.writeFile(String.join("\n\n", langLines), langFile);

        done.setDisable(false);
        PauseTransition transition = new PauseTransition(Duration.seconds(1.25));
        transition.setOnFinished(event -> {
            done.setDisable(true);
        });
        transition.play();
    }

    public void editName() {
        Tile tile = listView.getSelectionModel().getSelectedItem();
        if (tile == null) {
            return;
        }
        tile.setName(name.getText());
    }

    public void editDescription() {
        Tile tile = listView.getSelectionModel().getSelectedItem();
        if (tile == null) {
            return;
        }
        tile.setDescription(description.getText());
    }
}
