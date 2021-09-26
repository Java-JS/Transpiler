package transpiler.processors;

import java.util.ArrayList;
import java.util.List;

import static transpiler.constants.Constants.CLASS;
import static transpiler.constants.Constants.UNEXPECTED;

public class LexicalProcessor {
    boolean specialCase = false;
    private List<String> lexemes = new ArrayList<>();

    public List<String> interact(List<String> javaFile) {

        if (javaFile == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return new ArrayList<>();
        }

        for (String line : javaFile) {
            line = line.trim();

            System.out.println("\nLINE: " + line);

            interactOverLine(line).forEach(word -> {
                System.out.println("[FRAG: " + dictionary(word) + "=" + word + " ] ");
                lexemes.add(("[FRAG: " + dictionary(word) + "=" + word + " ] "));
            });
        }
        return lexemes;
    }

    private String dictionary(String word) {

        if (word.startsWith("\"") && word.endsWith("\"") && word.length() > 2)
            return "String found";

        String toReturn;

        switch (word) {
            case "(":
            case ")":
                toReturn = "parenthesis";
                break;

            case "{":
            case "}":
                toReturn = "curly-brackets";
                break;

            case "[":
            case "]":
                toReturn = "square-brackets";
                break;

            case ";":
                toReturn = "semicolon";
                break;

            case CLASS:
                toReturn = CLASS;
                break;

            case "imp":
            case "while":
            case "print":
            case "if":
            case "else":
                toReturn = "command";
                break;

            case "System.out.println":
                toReturn = "print";
                break;

            case "public":
            case "private":
            case "protected":
                toReturn = "acessor-modifier";
                break;

            case "int":
            case "Int":
            case "Float":
            case "String":
            case "boolean":
            case "Boolean":
            case "static":
            case "void":
                toReturn = "type";
                specialCase = true;
                break;

            case "main":
                toReturn = "main-method";
                break;

            case "*":
            case "+":
            case "/":
            case "-":
                toReturn = "operation";
                break;

            case " ":
                toReturn = "space";
                break;

            default:
                toReturn = UNEXPECTED;
        }

        return toReturn;
    }


    public List<String> interactOverLine(String line) {
        specialCase = false;
        List<String> result = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        for (int charPos = 0; charPos < line.length(); charPos++) {
            temp.append(line.charAt(charPos));

            if (!UNEXPECTED.equals(dictionary(String.valueOf(temp).trim()))) {
                result.add(String.valueOf(temp).trim());
                temp = new StringBuilder();

            } else if (!result.isEmpty() && result.get(result.size() - 1).equals(CLASS) && temp.length() > 1
                    && (line.charAt(charPos) == '{' || line.charAt(charPos) == ' ')) {
                result.add(String.valueOf(temp.substring(0, temp.length() - 1)).trim());
                temp = new StringBuilder();

                if (line.charAt(charPos) == '{')
                    result.add("{");
            } else if (specialCase && (line.charAt(charPos) == ' ' || line.charAt(charPos) == ')') && temp.length() > 1) {
                if (line.charAt(charPos) == ')') {
                    charPos--;
                }

                result.add(String.valueOf(temp).trim());
                temp = new StringBuilder();
                specialCase = false;
            }
        }
        return result;
    }
}