package transpiler.processors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transpiler.model.Lexeme;

import java.util.List;

import static transpiler.constants.Constants.*;

class LexicalProcessorTests {
    LexicalProcessor lexicalProcessor = new LexicalProcessor();

    @Test
    void testHelloWorldTokens() {
        Assertions.assertEquals(List.of("System.out.println",
                        "(",
                        "\"Hello World!\"",
                        ")",
                        ";"),
                lexicalProcessor.interactOverLine("System.out.println(\"Hello World!\");"));
    }

    @Test
    void testHelloWorldFragments() {
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(PRINT).command("System.out.println").build(),
                        Lexeme.builder().type(PARENTHESIS).command("(").build(),
                        Lexeme.builder().type(STRING_FOUND).command("\"Hello World!\"").build(),
                        Lexeme.builder().type(PARENTHESIS).command(")").build(),
                        Lexeme.builder().type(SEMICOLON).command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\");")));
    }

    @Test
    void testStringWithPlusSymbol() {
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(PRINT).command("System.out.println").build(),
                        Lexeme.builder().type(PARENTHESIS).command("(").build(),
                        Lexeme.builder().type(STRING_FOUND).command("\"Hello World!\"").build(),
                        Lexeme.builder().type(OPERATION).command("+").build(),
                        Lexeme.builder().type(STRING_FOUND).command("\"random\"").build(),
                        Lexeme.builder().type(PARENTHESIS).command(")").build(),
                        Lexeme.builder().type(SEMICOLON).command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\" + \"random\");")));
    }
}
