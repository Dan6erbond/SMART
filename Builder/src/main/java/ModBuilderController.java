import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ModBuilderController implements Initializable {

    public TextField filePath;
    public Button saveAsBtn;
    public Button saveBtn;
    public ComboBox kind;
    public Spinner stackSizeLimit;
    public Spinner sizeX;
    public Spinner sizeY;
    public Spinner sizeZ;
    public Spinner mapping;

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

        String[] kinds = {"material", "aliased", "object", "aliasedObject", "nothing"};
        kind.setItems(FXCollections.observableArrayList(kinds));

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()){
                try {
                    int newIn = Integer.parseInt(c.getControlNewText());
                    if (newIn < 0 || newIn > 999){
                        return null;
                    }
                } catch (NumberFormatException e){
                    System.out.println(e);
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> numberFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, filter);
        stackSizeLimit.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,1));
        stackSizeLimit.getEditor().setTextFormatter(numberFormatter);

        sizeX.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0));
        sizeY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0));
        sizeZ.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0));

        mapping.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,1));
    }

    public void addCategory(ActionEvent actionEvent) {
    }

    public void addVariant(ActionEvent actionEvent) {
    }
}
