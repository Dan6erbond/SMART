import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileHandlerTest {

    @org.junit.Test
    public void writeFile() {
    }

    @org.junit.Test
    public void readFile() {
    }

    @org.junit.Test
    public void getFiles() {
    }

    @org.junit.Test
    public void getSubFolders() {
        File[] folders = {
                new File("../mods/Xable's Clutter"),
                new File("../mods/lydociaClutter"),
                new File("../mods"),
                new File("../..")
        };

        for (File folder : folders){
            System.out.println(folder.getPath());

            long started = System.currentTimeMillis();
            FileHandler.getSubFolders(folder, new ArrayList<>());
            System.out.println("Recursive: " + (System.currentTimeMillis() - started) + "ms");

            started = System.currentTimeMillis();
            FileHandler.getSubFolders(folder);
            System.out.println("Looped: " + (System.currentTimeMillis() - started) + "ms");
        }
    }
}