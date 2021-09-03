import processors.LexicalProcessor;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner fileContent;

    public static void main(String[] args) {
        String fileName = "Java-Simple";
        openArchive(fileName);
        processContent(readFile());
        closeArchive();
    }

    private static void processContent(List<String> file) {
        LexicalProcessor lexicalProcessor = new LexicalProcessor();
        lexicalProcessor.interact(file);
    }

    private static void openArchive(String fileName) {
        try {
            fileContent = new Scanner(Paths.get("src/main/resources/" + fileName + ".java"));
        } catch (IOException errIO) {
            System.err.println("Error while opening file");
        }
    }

    private static List<String> readFile() {
        List<String> content = new ArrayList<>();
        try {
            while (fileContent.hasNext()) {
                String a = fileContent.nextLine();
                if (a == null) {
                    return null;
                }
                content.add(a);
            }
        } catch (Exception err) {
            System.err.println("Error " + err);
        }
        return content;
    }

    private static void closeArchive() {
        if (fileContent != null)
            fileContent.close();
    }
}
