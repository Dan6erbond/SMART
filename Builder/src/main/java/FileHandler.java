import java.io.*;

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
}
