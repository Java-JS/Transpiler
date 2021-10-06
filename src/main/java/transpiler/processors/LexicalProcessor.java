package transpiler.processors;

import java.util.ArrayList;
import java.util.List;

import static transpiler.constants.Constants.CLASS;
import static transpiler.constants.Constants.UNEXPECTED;

public class LexicalProcessor {
    boolean specialCase = false;
    private List<String> lexemes = new ArrayList<>();

    public List<String> interact(final List<String> javaFile) {

        if (javaFile == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return new ArrayList<>();
        }

        for (String line : javaFile) {
            line = line.trim();

            System.out.println("\nLINE: " + line);

            interactOverLine(line).forEach(word -> {
                System.out.println("[FRAG: " + syntacticParser(word) + "=" + word + "]");
                lexemes.add(("[FRAG: " + syntacticParser(word) + "=" + word + "]"));
            });
        }
        return lexemes;
    }

    private String syntacticParser(String word) {
        if (word.startsWith("\"") && word.endsWith("\"") && word.length() > 2)
            return "String found";

        if (word.matches("[0-9]")) {
            return "number";
        }

        switch (word) {
            case "(":
            case ")":
                return "parenthesis";

            case "{":
            case "}":
                return "curly-brackets";

            case "[":
            case "]":
                return "square-brackets";

            case ";":
                return "semicolon";

            case CLASS:
                return CLASS;

            case "imp":
            case "while":
            case "print":
            case "if":
            case "else":
                return "command";

            case "System.out.println":
                return "print";

            case "public":
            case "private":
            case "protected":
                return "accessor-modifier";

            case "int":
            case "Int":
            case "Float":
            case "String":
            case "boolean":
            case "Boolean":
            case "static":
            case "void":
                specialCase = true;
                return "type";

            case "main":
                return "main-method";

            case "*":
            case "+":
            case "/":
            case "-":
            case "=":
                return "operation";

            case " ":
                return "space";

            default:
                return UNEXPECTED;
        }
    }

    public List<String> interactOverLine(String line) {
        specialCase = false;
        List<String> result = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        for (int charPos = 0; charPos < line.length(); charPos++) {
            temp.append(line.charAt(charPos));

            if (!UNEXPECTED.equals(syntacticParser(String.valueOf(temp).trim()))) {
                result.add(String.valueOf(temp).trim());
                temp = new StringBuilder();

            } else if (!result.isEmpty() && result.get(result.size() - 1).equals(CLASS) && temp.length() > 1
                    && (line.charAt(charPos) == '{' || line.charAt(charPos) == ' ')) {
                result.add(String.valueOf(temp.substring(0, temp.length() - 1)).trim());
                temp = new StringBuilder();

                if (line.charAt(charPos) == '{')
                    result.add("{");
            } else if (specialCase && (line.charAt(charPos) == ';' || line.charAt(charPos) == ' ' || line.charAt(charPos) == ')') && temp.length() > 1) {
                if (!syntacticParser(String.valueOf(line.charAt(charPos))).equals(UNEXPECTED)) {
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