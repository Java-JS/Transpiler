package transpiler.processors;

import transpiler.domain.JavaVariable;
import transpiler.domain.Lexeme;
import transpiler.enumerator.LexemeType;

import java.util.ArrayList;
import java.util.List;

public class SemanticProcessor {

    public static void execution(final List<Lexeme> lexemes) {
        List<String> execution = new ArrayList<>();
        List<JavaVariable> variables = new ArrayList<>();

        boolean insideClass = false;

        System.out.println();

        for (int i = 0; i < lexemes.size(); i++) {


            switch (lexemes.get(i).getType()) {

                case CLASS -> {

                    if (i >= 1 && lexemes.get(i - 1).getCommand().equals("public")) {
                        if (lexemes.get(i + 1).getType().equals(LexemeType.UNEXPECTED)) {
                            System.out.println(lexemes.get(i));
                            insideClass = true;

                        }
                    }
                }


                case TYPE -> {

                    if (lexemes.get(i + 1).getType().equals(LexemeType.UNEXPECTED)) {
                        System.out.println(lexemes.get(i));
                    }

                    if (lexemes.get(i + 1).getCommand().equals("[") && lexemes.get(i + 2).getCommand().equals("]")) {
                        if (lexemes.get(i + 3).getType().equals(LexemeType.UNEXPECTED)) {
                            System.out.println(lexemes.get(i + 3));
                        }
                    }


                }

            }

        }

        // TODO check if the variable was already declared or not declared
        // TODO check variable type if matches the content
        // TODO check if the var is inside a class

//        System.out.println(execution);

    }


    public static void main(String[] args) {
        List<Lexeme> test = new ArrayList<>();
        test.add(Lexeme.builder().type(LexemeType.ACCESSOR_MODIFIER).command("public").build());
        test.add(Lexeme.builder().type(LexemeType.CLASS).command("class").build());
        test.add(Lexeme.builder().type(LexemeType.UNEXPECTED).command("ClassTest").build());

        execution(test);
    }
}
