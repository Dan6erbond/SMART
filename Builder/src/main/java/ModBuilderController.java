import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ModBuilderController implements Initializable {

    public TextField filePath;
    public Button saveAsBtn;
    public Button saveBtn;

    private File selectedFile;

    public void selectTile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tile Files", "*.tile"));
        chooser.setTitle("Select Tile File");
        String currentDir = filePath.getText().isEmpty() ? System.getProperty("user.dir") : new File(filePath.getText()).getParentFile().getPath();
        File defaultDirectory = new File(currentDir);
        chooser.setInitialDirectory(defaultDirectory);
        selectedFile = chooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (selectedFile == null) {
            return;
        }

        filePath.setText(selectedFile.getPath());
        saveBtn.setDisable(false);
    }

    public void save(ActionEvent actionEvent) {
        System.out.println(selectedFile.getPath());
    }

    public void saveAs(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tile Files", "*.tile"));
        chooser.setTitle("Select Tile File");
        String currentDir = filePath.getText().isEmpty() ? System.getProperty("user.dir") : new File(filePath.getText()).getParentFile().getPath();
        File defaultDirectory = new File(currentDir);
        chooser.setInitialDirectory(defaultDirectory);
        File saveFile = chooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (saveFile == null) {
            return;
        }

        System.out.println(saveFile.getPath());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveBtn.setDisable(true);
        saveAsBtn.setDisable(true);
    }
}
