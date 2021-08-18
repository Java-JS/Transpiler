import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static Scanner fileContent;

    public static void main(String[] args) {
        String fileName = "Java-Simple";
        openArchive(fileName);
    }

    private static void openArchive(String fileName) {
        try {
            fileContent = new Scanner(Paths.get("src/main/resources/" + fileName + ".java"));
        } catch (IOException errIO) {
            System.err.println("Error while opening file");
        }
    }
}
