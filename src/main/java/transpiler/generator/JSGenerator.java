package transpiler.generator;

import transpiler.domain.Lexeme;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static transpiler.enumerator.LexemeType.JAVA_IMPORT;
import static transpiler.enumerator.LexemeType.VOID;

public class CodeGenerator {

    public List<Lexeme> lexemes;

    public CodeGenerator(List<Lexeme> lexeme) {
        this.lexemes = lexeme;
    }

    public void generate() throws IOException {
        System.out.println();
        FileWriter arq = new FileWriter("src/main/resources/resultado.js");
        PrintWriter file = new PrintWriter(arq);
        boolean onParenthesis = false;

        boolean scannerDeclared = false;

        for (int i = 0; i < lexemes.size(); i++) {

            switch (lexemes.get(i).getType()) {

                case PARENTHESIS:
                    if (lexemes.get(i).getCommand().equals("("))
                        onParenthesis = true;
                    if (lexemes.get(i).getCommand().equals(")"))
                        onParenthesis = false;

                    file.write(lexemes.get(i).getCommand());
                    break;

                case TYPE:
                    System.out.println(lexemes.get(i));
                    if (!onParenthesis && lexemes.get(i + 1).getType() != (VOID))
                        file.write("let ");
                    break;

                case SQUARE_BRACKETS:
                    if (!onParenthesis)
                        file.write(lexemes.get(i).getCommand());
                    break;

                case STRING_FOUND:
                case OPERATION:
                case UNEXPECTED:
                case NUMBER:
                case CURLY_BRACKETS:
                case MAIN_METHOD:
                case COMMAND:
                    file.write(lexemes.get(i).getCommand());
                    break;

                case CLASS:
                    file.write(lexemes.get(i).getCommand() + " ");
                    break;


                case PRINT:
                    file.write("console.log");
                    break;


                case SEMICOLON:
                    if (!lexemes.get(i - 1).getType().equals(JAVA_IMPORT))
                        file.write(";\n");
                    break;

                case LINE_FEED:
                    file.write("\n");
                    break;

                case SCANNER:
                    scannerDeclared = true;
                    break;

                default:
                    System.out.println("Is this really need? " + lexemes.get(i).getCommand());
            }

        }

        file.write("instance = new HelloWorld();\n");
        file.write("instance.main();");

        arq.close();

    }
}
