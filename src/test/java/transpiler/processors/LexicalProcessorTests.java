package transpiler.processors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transpiler.model.Lexeme;

import java.util.List;

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
                        Lexeme.builder().type("print").command("System.out.println").build(),
                        Lexeme.builder().type("parenthesis").command("(").build(),
                        Lexeme.builder().type("String found").command("\"Hello World!\"").build(),
                        Lexeme.builder().type("parenthesis").command(")").build(),
                        Lexeme.builder().type("semicolon").command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\");")));
    }

    @Test
    void testStringWithPlusSymbol() {
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type("print").command("System.out.println").build(),
                        Lexeme.builder().type("parenthesis").command("(").build(),
                        Lexeme.builder().type("String found").command("\"Hello World!\"").build(),
                        Lexeme.builder().type("operation").command("+").build(),
                        Lexeme.builder().type("String found").command("\"random\"").build(),
                        Lexeme.builder().type("parenthesis").command(")").build(),
                        Lexeme.builder().type("semicolon").command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\" + \"random\");")));
    }
}
