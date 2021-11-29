package transpiler.generator;

import lombok.Builder;
import lombok.Data;
import transpiler.domain.Lexeme;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static transpiler.enumerator.LexemeType.*;

@Data
@Builder
public class JSGenerator {

    public static void generate(List<Lexeme> lexemes) throws IOException {
        System.out.println("\n======JS GENERATOR======");
        FileWriter arq = new FileWriter("src/main/resources/resultado.js");
        PrintWriter file = new PrintWriter(arq);
        boolean onParenthesis = false;

        boolean scannerDeclared = false;

        for (int i = 0; i < lexemes.size(); i++) {
//        javaFile.forEach(line -> interactOverLine(line.trim()).forEach(word ->
//                lexemes.add(Lexeme.builder().type(syntacticParser(word)).command(word).build())));

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

                    if (lexemes.get(i + 1).getType().equals(UNEXPECTED)) {
                        lexemes.remove(i + 1);
                    }

                    file.write("const prompt = require('prompt-sync')({sigint: true});\n");
                    break;

                case SCANNER_NEXT:
                    file.write("prompt('')");
                    break;

                default:
                    System.out.println("Is this really need? " + lexemes.get(i).getCommand());
            }

        }

        file.write("\ninstance = new HelloWorld();\n");
        file.write("instance.main();");

        arq.close();

    }
}
