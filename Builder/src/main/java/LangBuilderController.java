import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LangBuilderController implements Initializable {

    public TextField folderPath;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void selectModFolder(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        String currentDir = System.getProperty("user.dir");
        File defaultDirectory = new File(currentDir);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(((Node)actionEvent.getSource()).getScene().getWindow());
        folderPath.setText(selectedDirectory.toString());

        File[] dirs = {selectedDirectory};
        ArrayList<File> directories = new ArrayList<>(Arrays.asList(dirs));
        directories = getFolders(selectedDirectory, directories);

        ArrayList<File> tileFiles = new ArrayList<>();
        for (File dir : directories){
            File[] tfs = dir.listFiles((current, name) -> new File(current, name).getName().endsWith(".tile"));
            if (tfs != null && tfs.length > 0){
                tileFiles.addAll(Arrays.asList(tfs));
            }
        }

        for (File tileFile : tileFiles){
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(tileFile.getPath())))
            {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null)
                {
                    contentBuilder.append(sCurrentLine).append("\n");
                }

                JSONObject object = new JSONObject(contentBuilder.toString());
                String code = object.getString("code");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<File> getFolders(File folder, ArrayList<File> directories){
        File[] subDirectories = folder.listFiles((current, name) -> new File(current, name).isDirectory());
        if (subDirectories == null || subDirectories.length <= 0){
            return directories;
        }
        for (File file : subDirectories){
            directories.add(file);
            File[] subDirs = file.listFiles((current, name) -> new File(current, name).isDirectory());
            if (subDirs != null && subDirs.length > 0){
                directories = getFolders(file, directories);
            }
        }
        return directories;
    }
}
