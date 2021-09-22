package transpiler.processors;

import java.util.ArrayList;
import java.util.List;

public class LexicalProcessor {

    public void interact(List<String> javaFile) {

        if (javaFile == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return;
        }

        for (String line : javaFile) {
            line = line.trim();

            System.out.println("\nLINE: " + line);

            interactOverLine(line).forEach(word -> {
//                System.out.println("return interact over line: " + word);
                System.out.println("[FRAG: " + dictionary(word) + "=" + word + " ] " + "\n");
            });
        }
    }

    private static String dictionary(String word) {

        if (word.startsWith("\""))
            return "String: " + word;

        switch (word) {
            case "(":
            case ")":
                return "parenthesis";

            case "{":
            case "}":
                return "curly-brackets";

            case ";":
                return "semicolon";

            case "imp":
            case "while":
            case "print":
            case "if":
            case "else":
                return "command";

            case "System.out.println":
                return "print";

            case "public static void main":
                return "acessor-modifier";

            case "int":
            case "Int":
            case "Float":
            case "String":
            case "String[]":
            case "boolean":
            case "Boolean":
                return "type";

            case "*":
            case "+":
            case "/":
            case "-":
                return "operation";

            default:
                return "unexpected";
        }
    }


    public List<String> interactOverLine(String line) {

        List<String> result = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        boolean special = false;

        for (int charPos = 0; charPos < line.length(); charPos++) {
            temp.append(line.charAt(charPos));

            switch (line.charAt(charPos)) {
                case '{':
                case '}':
                case ' ':
                case ';':
                case '(':
                case ')':
                    special = true;
                    break;
                default:
                    break;
            }


//            System.out.println(dictionary(String.valueOf(temp)));
//            System.out.println("palavra =" + temp);
            if (special && !"unexpected".equals(dictionary(String.valueOf(temp)))) {
//                System.out.println("Palavra existe no dicionário =" + temp);

                result.add(String.valueOf(temp));
                temp = new StringBuilder();
                special = false;
            }

        }
        return result;
    }
}
