package transpiler.processors;

import transpiler.domain.Lexeme;
import transpiler.enumerator.LexemeType;
import transpiler.domain.JavaVariable;

import java.util.ArrayList;
import java.util.List;

import static transpiler.constants.Constants.*;

public class SemanticProcessor {

    public static void execution(final List<Lexeme> lexemes) {
        List<String> execution = new ArrayList<>();
        List<JavaVariable> variables = new ArrayList<>();

        for (int i = 0; i < lexemes.size(); i++) {

            if (lexemes.get(i).getType().equals(LexemeType.CLASS)) {
                if (lexemes.get(i - 1).getCommand().equals("public")) {
                    if (lexemes.get(i + 1).getType().equals(LexemeType.UNEXPECTED)) {
                        System.out.println("Class found!");
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
        test.add(Lexeme.builder().type(LexemeType.CLASS).command(CLASS).build());
        test.add(Lexeme.builder().type(LexemeType.UNEXPECTED).command("ClassTest").build());

        execution(test);
    }
}
