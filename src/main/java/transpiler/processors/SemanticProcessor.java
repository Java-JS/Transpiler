package transpiler.processors;

import transpiler.domain.JavaVariable;
import transpiler.domain.Lexeme;
import transpiler.enumerator.LexemeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SemanticProcessor {

    public static void execution(final List<Lexeme> lexemes) {
        List<String> execution = new ArrayList<>();
        List<JavaVariable> variables = new ArrayList<>();

        boolean insideClass = false;

        System.out.println();

        for (int i = 0; i < lexemes.size(); i++) {


            switch (lexemes.get(i).getType()) {

                case CLASS -> {

                    if (i >= 1 && lexemes.get(i - 1).getCommand().equals("public") && lexemes.get(i + 1).getType().equals(LexemeType.UNEXPECTED)) {
                        insideClass = true;

                    }
                }


                case TYPE -> {

                    if (lexemes.get(i + 1).getType().equals(LexemeType.UNEXPECTED)) {
                        if (elementExistsOnVariables(variables, lexemes.get(i + 1).getCommand())) {
                            System.out.println("ERROR! The variable " + lexemes.get(i + 1).getCommand() + " should not be declared twice.");
                        } else if (!insideClass) {
                            System.out.println("ERROR! The variable " + lexemes.get(i + 1).getCommand() + " must be inside a class.");
                        } else
                            variables.add(JavaVariable.builder().type(lexemes.get(i).getCommand()).name(lexemes.get(i + 1).getCommand()).build());
                    }

                    if (lexemes.get(i + 1).getCommand().equals("[") && lexemes.get(i + 2).getCommand().equals("]")) {
                        if (lexemes.get(i + 3).getType().equals(LexemeType.UNEXPECTED)) {
                            if (elementExistsOnVariables(variables, lexemes.get(i + 3).getCommand())) {
                                System.out.println("ERROR! The variable " + lexemes.get(i + 3).getCommand() + " should not be declared twice.");
                            } else if (!insideClass) {
                                System.out.println("ERROR! The variable " + lexemes.get(i + 3).getCommand() + " must be inside a class.");
                            } else
                                variables.add(JavaVariable.builder().type(lexemes.get(i).getCommand() + "[]").name(lexemes.get(i + 3).getCommand()).build());
                        }
                    }


                }

                case UNEXPECTED -> {
                    if (lexemes.get(i + 1).getCommand().equals("=")) {

                        if (elementExistsOnVariables(variables, lexemes.get(i).getCommand())) {


                            JavaVariable javaVariable = getElementOnVariables(variables, lexemes.get(i).getCommand());
                            String match;
                            switch (javaVariable.getType()) {
                                case "Float", "int", "Integer", "Double", "double" -> match = "number";
                                case "String" -> match = "string_found";
                                case "Boolean", "boolean" -> match = "boolean";
                                default -> match = "error";
                            }

                            if (lexemes.get(i + 2).getType().equals(LexemeType.NUMBER) && lexemes.get(i + 2).getType().getLexemeType().equals(match)){
                                System.out.println("That's ok! Number");
                            } else if (lexemes.get(i + 2).getType().equals(LexemeType.STRING_FOUND) && lexemes.get(i + 2).getType().getLexemeType().toLowerCase().contains(match)){
                                System.out.println("That's ok! String");
                            } else System.out.println("ERROR!!! wrong assignation");



                        } else
                            System.out.println("ERROR! The variable " + lexemes.get(i).getCommand() + " was not declared.");
                    }
                }
            }

        }
    }

    public static boolean elementExistsOnVariables(List<JavaVariable> variables, String name) {
        Optional<JavaVariable> variable = variables.stream().filter(javaVariable -> javaVariable.getName().equals(name)).findFirst();

        return variable.isPresent();
    }

    public static JavaVariable getElementOnVariables(List<JavaVariable> variables, String name) {
        Optional<JavaVariable> variable = variables.stream().filter(javaVariable -> javaVariable.getName().equals(name)).findFirst();

        return variable.orElse(null);
    }
}
