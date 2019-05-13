import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LangBuilderController implements Initializable {

    public TextField folderPath;
    public ListView<Tile> listView;
    public TextField name;
    public TextArea description;
    public Button writeFileBtn;

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
            name.setText(tile.getName());
            description.setText(tile.getDescription());
        });

        writeFileBtn.setDisable(true);
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
        listView.getItems().addAll(getTiles(selectedDirectory));
    }

    private ArrayList<Tile> getTiles(File folder) throws IOException {
        File[] dirs = {folder};
        ArrayList<File> directories = new ArrayList<>(Arrays.asList(dirs));
        directories = getFolders(folder, directories);

        ArrayList<Tile> tiles = new ArrayList<>();
        for (File dir : directories) {
            File[] tfs = dir.listFiles((current, name) -> new File(current, name).getName().endsWith(".tile"));
            if (tfs != null && tfs.length > 0) {
                for (File tf : tfs) {
                    tiles.add(new Tile(tf, lang));
                }
            }
        }

        return tiles;
    }

    private ArrayList<File> getFolders(File folder, ArrayList<File> directories) {
        File[] subDirectories = folder.listFiles((current, name) -> new File(current, name).isDirectory());
        if (subDirectories == null || subDirectories.length <= 0) {
            return directories;
        }
        for (File file : subDirectories) {
            directories.add(file);
            File[] subDirs = file.listFiles((current, name) -> new File(current, name).isDirectory());
            if (subDirs != null && subDirs.length > 0) {
                directories = getFolders(file, directories);
            }
        }
        return directories;
    }

    public void writeLangFile(ActionEvent actionEvent) throws IOException {
        ArrayList<String> langLines = new ArrayList<>();
        langLines.add("language.code=en-GB\nlanguage=English");

        for (Tile tile : listView.getItems()) {
            langLines.add(tile.getLang());
        }

        File langFile = new File(selectedDirectory.getPath(), selectedDirectory.getName() + ".lang");
        FileHandler.writeFile(String.join("\n\n", langLines), langFile);
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
