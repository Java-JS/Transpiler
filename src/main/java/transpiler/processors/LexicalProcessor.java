package transpiler.processors;

import transpiler.model.Lexeme;

import java.util.ArrayList;
import java.util.List;

import static transpiler.constants.Constants.*;

public class LexicalProcessor {
    boolean specialCase = false;
    private final List<Lexeme> lexemes = new ArrayList<>();

    public List<Lexeme> interact(final List<String> javaFile) {

        if (javaFile == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return new ArrayList<>();
        }

        for (String line : javaFile) {
            System.out.println("\nLINE: " + line.trim());

            interactOverLine(line.trim()).forEach(word -> {
                lexemes.add(Lexeme.builder().type(syntacticParser(word)).command(word).build());
                System.out.println(lexemes.get(lexemes.size() -1));
            });
        }

        System.out.println(lexemes);

        return lexemes;
    }

    private String syntacticParser(String word) {
        if (word.startsWith("\"") && word.endsWith("\"") && word.length() > 2)
            return "String found";

        if (word.matches("[0-9]")) {
            return NUMBER;
        }

        switch (word) {
            case "(":
            case ")":
                return PARENTHESIS;

            case "{":
            case "}":
                return CURLY_BRACKETS;

            case "[":
            case "]":
                return SQUARE_BRACKETS;

            case ";":
                return SEMICOLON;

            case CLASS:
                return CLASS;

            case "imp":
            case "while":
            case "print":
            case "if":
            case "else":
                return COMMAND;

            case "System.out.println":
                return PRINT;

            case "public":
            case "private":
            case "protected":
                return ACCESSOR_MODIFIER;

            case "int":
            case "Int":
            case "Float":
            case "String":
            case "boolean":
            case "Boolean":
            case "static":
            case "void":
                specialCase = true;
                return TYPE;

            case "main":
                return MAIN_METHOD;

            case "*":
            case "+":
            case "/":
            case "-":
            case "=":
                return OPERATION;

            case " ":
                return SPACE;

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

                if (line.charAt(charPos) == '{')
                    result.add("{");
                temp = new StringBuilder();

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