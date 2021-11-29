package transpiler.processors;

import transpiler.domain.Lexeme;
import transpiler.enumerator.LexemeType;

import java.util.ArrayList;
import java.util.List;

import static transpiler.constants.Constants.*;

public class LexicalProcessor {
    boolean specialCase = false;
    private String scan;
    private final List<Lexeme> lexemes = new ArrayList<>();

    public List<Lexeme> interact(final List<String> javaFile) {
        System.out.println("\n======LEXICAL/SYNTACTIC PROCESSOR======");

        if (javaFile == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return new ArrayList<>();
        }

        javaFile.forEach(line -> {
            System.out.println("\n" + line.trim());
            interactOverLine(line.trim()).forEach(word -> {
                lexemes.add(Lexeme.builder().type(syntacticParser(word)).command(word).build());
                System.out.println(lexemes.get(lexemes.size() - 1));
            });
        });

        return lexemes;
    }

    private LexemeType syntacticParser(String word) {
        if (word.startsWith("\"") && word.endsWith("\"") && word.length() > 2)
            return LexemeType.STRING_FOUND;

        if (word.matches("[0-9]")) {
            return LexemeType.NUMBER;
        }

        if (word.equals("new Scanner(System.in);")) {
            return LexemeType.SCANNER;
        }

        if (word.equals("\n"))
            return LexemeType.LINE_FEED;

        if (word.equals(scan))
            return LexemeType.SCANNER_IMPL;

        switch (word) {
            case "(", ")":
                return LexemeType.PARENTHESIS;

            case "{", "}":
                return LexemeType.CURLY_BRACKETS;

            case "[", "]":
                return LexemeType.SQUARE_BRACKETS;

            case ";":
                return LexemeType.SEMICOLON;

            case CLASS:
                return LexemeType.CLASS;

            case "while", "print", "if", "else", "&&":
                return LexemeType.COMMAND;

            case "System.out.println":
                return LexemeType.PRINT;

            case "public", "private", "protected":
                return LexemeType.ACCESSOR_MODIFIER;

            case "int", "Int", "Float", "String", "boolean", "Boolean ", "Double", "double", "static":
                specialCase = true;
                return LexemeType.TYPE;

            case "void":
                return LexemeType.VOID;

            case "main":
                return LexemeType.MAIN_METHOD;

            case "*", "+", "/", "-", "=", "<", ">":
                return LexemeType.OPERATION;

            case " ":
                return LexemeType.SPACE;

            case ".":
                return LexemeType.POINT;

            case "import", "java.util.Scanner":
                return LexemeType.JAVA_IMPORT;

            case "Scanner":
                return LexemeType.SCANNER;

            case "new":
                return LexemeType.NEW;

            case "//":
                return LexemeType.COMMENT;

            case "reader":
                return LexemeType.SCANNER_NEXT;

            default:
                return LexemeType.UNEXPECTED;
        }
    }

    public List<String> interactOverLine(String line) {
        specialCase = false;
        List<String> result = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        if (line.trim().equals(""))
            result.add("\n");

        if (line.contains("= new Scanner(System.in);")) {
            line = line.replace("= new Scanner(System.in);", "");
        }

        if (line.startsWith("//")) {
            result.add("//");
        }

        for (int charPos = 0; charPos < line.length(); charPos++) {
            temp.append(line.charAt(charPos));

            if (!LexemeType.UNEXPECTED.equals(syntacticParser(String.valueOf(temp).trim()))) {
                result.add(String.valueOf(temp).trim());
                temp = new StringBuilder();


            } else if (!result.isEmpty() && result.get(result.size() - 1).equals(LexemeType.SCANNER.getType()) && (line.charAt(charPos) == ' ') && temp.length() > 1 && syntacticParser(String.valueOf(temp).trim()).equals(LexemeType.UNEXPECTED)) {
                scan = String.valueOf(temp.substring(0, temp.length() - 1)).trim();
                result.add(scan);

                temp = new StringBuilder();

            } else if (String.valueOf(temp).trim().equals("nextInt") || String.valueOf(temp).trim().equals("nextDouble") || String.valueOf(temp).trim().equals("nextDouble")) {

                result.add("reader");
                charPos = line.length() - 2;
                temp = new StringBuilder();

            } else if (!result.isEmpty() && result.get(result.size() - 1).equals(CLASS) && temp.length() > 1
                    && (line.charAt(charPos) == '{' || line.charAt(charPos) == ' ')) {
                result.add(String.valueOf(temp.substring(0, temp.length() - 1)).trim());

                if (line.charAt(charPos) == '{')
                    result.add("{");
                temp = new StringBuilder();

            } else if ((line.charAt(charPos) == ';' || line.charAt(charPos) == ' ' || line.charAt(charPos) == ')') && temp.length() > 1) {
                if (!syntacticParser(String.valueOf(line.charAt(charPos))).equals(LexemeType.UNEXPECTED) && temp.charAt(0) != '"') {

                    result.add(temp.substring(0, temp.length() - 1).trim());

                    charPos--;
                    temp = new StringBuilder();
                }

                if (temp.toString().length() > 1 && temp.charAt(0) == '"')
                    continue;

                if (!"".equals((String.valueOf(temp.substring(0, temp.length())).trim())))
                    result.add(String.valueOf(temp).trim());

                temp = new StringBuilder();
                specialCase = false;
            }
        }
        return result;
    }
}