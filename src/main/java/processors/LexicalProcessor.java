package processors;

import java.util.List;

public class LexicalProcessor {

    StringBuilder phraseBuilder = new StringBuilder();
    private int lineCharPos;
    private static boolean errors = false;

    public String interact(List<String> JAVA_FILE) {

        if (JAVA_FILE == null) {
            System.err.println("ERRO! Não há entradas válidas.");
            return null;
        }

        for (String line : JAVA_FILE) {
            System.out.println(line);

            System.out.println("LINE: " + line);

            while (lineCharPos < line.length()) {
                String word = interactingLine(line);

                System.out.println("WORD: " + word);
                System.out.println("POS: " + lineCharPos);


                phraseBuilder.append("[FRAG: ").append("( ").append(dictionary(word)).append(" )").append("] ");
                lineCharPos++;
            }
        }

        System.out.println("Algum erro? " + (errors ? "Sim" : "Não"));

        return String.valueOf(phraseBuilder);
    }


    private static String dictionary(String word) {

        if (word.startsWith("\""))
            return "String: " + word;

        switch (word) {
            case "(":
            case ")":
                return "parenthesis: " + word;
            case ";":
                return "semicolon: " + word;
            case "imp":
            case "while":
            case "print":
            case "if":
            case "else":
                return "command: " + word;
            case "int":
            case "float":
            case "string":
            case "boolean":
                return "type: " + word;
            case "*":
            case "+":
            case "/":
            case "-":
                return "operation: " + word;
            default:
                warning("UNEXPECTED " + word);
                return "unexpected: " + word;
        }
    }


    public String interactingLine(String line) {
        StringBuilder temp = new StringBuilder();
        boolean special = false;
//        boolean insideParentheses = false;

        while (lineCharPos < line.length()) {
//            if (line.charAt(lineCharPos) == ' ') {
//                warning("SPACE");
//                special = true;
//            }

            if (line.charAt(lineCharPos) == '(') {
//                warning("PARENTHESIS");
//                insideParentheses = true;
                special = true;
            }

            if (line.charAt(lineCharPos) == ')') {
//                warning("PARENTHESIS");
//                insideParentheses = false;
                special = true;
            }


            if (line.charAt(lineCharPos) == ';') {
//                warning("SEMICOLON");
                special = true;
            }

            if (temp.length() > 0 && special) {
                lineCharPos--;
                return String.valueOf(temp);
            }

            temp.append(line.charAt(lineCharPos));
            lineCharPos++;
        }
        return String.valueOf(temp);
    }



    private static void warning(String message) {
        System.err.println("ERROR! " + message);
        errors = true;
    }
}
