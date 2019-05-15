import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {

    public static void writeFile(String str, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
        writer.write(str);
        writer.close();
    }

    public static String readFile(File file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }

            return contentBuilder.toString();
        }
    }

    public static ArrayList<File> getFiles(String extension, File folder, boolean subFolders) throws IOException {
        File[] dirs = {folder};
        ArrayList<File> directories = new ArrayList<>(Arrays.asList(dirs));
        if (subFolders){
            directories = getSubFolders(folder, directories);
        }

        ArrayList<File> files = new ArrayList<>();
        for (File dir : directories) {
            File[] filesInDir = dir.listFiles((current, name) -> new File(current, name).getName().toLowerCase().endsWith(extension.toLowerCase()));
            if (filesInDir != null && filesInDir.length > 0) {
                files.addAll(Arrays.asList(filesInDir));
            }
        }

        return files;
    }

    public static ArrayList<File> getSubFolders(File folder, ArrayList<File> directories) {
        File[] subDirectories = folder.listFiles((current, name) -> new File(current, name).isDirectory());
        if (subDirectories == null || subDirectories.length <= 0) {
            return directories;
        }
        for (File file : subDirectories) {
            directories.add(file);
            File[] subDirs = file.listFiles((current, name) -> new File(current, name).isDirectory());
            if (subDirs != null && subDirs.length > 0) {
                directories = getSubFolders(file, directories);
            }
        }
        return directories;
    }
}
