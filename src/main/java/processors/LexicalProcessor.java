package processors;

import java.util.List;

public class LexicalProcessor {

    public static List<String> interact(List<String> JAVA_FILE) {

        if (JAVA_FILE == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return null;
        }

        for (String line : JAVA_FILE) {
            System.out.println(line);

            if (line.startsWith("import")){
                System.out.println("import encontrado");
            }

            if (line.startsWith("public class")){
                System.out.println("classe encontrada");
            }

        }

        return JAVA_FILE;
    }

}
