package transpiler.processors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transpiler.domain.Lexeme;
import transpiler.enumerator.LexemeType;

import java.util.List;

class LexicalProcessorTests {
    LexicalProcessor lexicalProcessor = new LexicalProcessor();

    @Test
    void testHelloWorldTokens() {
        Assertions.assertEquals(List.of("System.out.println",
                        "(",
                        "\"Hello World!\"",
                        "a",
                        ";"),
                lexicalProcessor.interactOverLine("System.out.println(\"Hello World!\");"));
    }

    @Test
    void testHelloWorldFragments() {
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(LexemeType.PRINT).command("System.out.println").build(),
                        Lexeme.builder().type(LexemeType.PARENTHESIS).command("(").build(),
                        Lexeme.builder().type(LexemeType.STRING_FOUND).command("\"Hello World!\"").build(),
                        Lexeme.builder().type(LexemeType.PARENTHESIS).command(")").build(),
                        Lexeme.builder().type(LexemeType.SEMICOLON).command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\");")));
    }

    @Test
    void testStringWithPlusSymbol() {
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(LexemeType.PRINT).command("System.out.println").build(),
                        Lexeme.builder().type(LexemeType.PARENTHESIS).command("(").build(),
                        Lexeme.builder().type(LexemeType.STRING_FOUND).command("\"Hello World!\"").build(),
                        Lexeme.builder().type(LexemeType.OPERATION).command("+").build(),
                        Lexeme.builder().type(LexemeType.STRING_FOUND).command("\"random\"").build(),
                        Lexeme.builder().type(LexemeType.PARENTHESIS).command(")").build(),
                        Lexeme.builder().type(LexemeType.SEMICOLON).command(";").build()),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\" + \"random\");")));
    }

    @Test
    void testInt() {
        //   int y=32;
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(LexemeType.TYPE).command("int").build(),
                        Lexeme.builder().type(LexemeType.UNEXPECTED).command("y").build(),
                        Lexeme.builder().type(LexemeType.OPERATION).command("=").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("3").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("2").build()),
                lexicalProcessor.interact(List.of("int y = 32")));
    }

    @Test
    void testF() {
        //  float price=2100.0
        // teste reprovado por erro na linha 71, onde o codigo não está considerando o ponto
        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(LexemeType.TYPE).command("Float").build(),
                        Lexeme.builder().type(LexemeType.UNEXPECTED).command("price").build(),
                        Lexeme.builder().type(LexemeType.OPERATION).command("=").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("2").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("1").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("0").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("0").build(),
                        Lexeme.builder().type(LexemeType.UNEXPECTED).command(".").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("0").build()),
                lexicalProcessor.interact(List.of("Float price = 2100.0")));
    }

    @Test
    void test4() {
        // float price=650.5
        // teste reprovado por erro na linha 88, onde o codigo não está considerando o ponto

        Assertions.assertEquals(List.of(
                        Lexeme.builder().type(LexemeType.TYPE).command("Float").build(),
                        Lexeme.builder().type(LexemeType.UNEXPECTED).command("price").build(),
                        Lexeme.builder().type(LexemeType.OPERATION).command("=").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("6").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("5").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("0").build(),
                        Lexeme.builder().type(LexemeType.UNEXPECTED).command(".").build(),
                        Lexeme.builder().type(LexemeType.NUMBER).command("5").build()),
                lexicalProcessor.interact(List.of("Float price = 650.5")));
    }

    @Test
    void test5(){

     //   if (horario > 5 && horario <= 12) {System.out.println("Bom dia");
        Assertions.assertEquals(List.of(
                Lexeme.builder().type(LexemeType.COMMAND).command("if").build(),
                Lexeme.builder().type(LexemeType.SPACE).command(" ").build(),
                Lexeme.builder().type(LexemeType.PARENTHESIS).command("(").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("h").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("o").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("r").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("a").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("r").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("i").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("o").build(),
                Lexeme.builder().type(LexemeType.SPACE).command(" ").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command(">").build(),
                Lexeme.builder().type(LexemeType.NUMBER).command("5").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("&&").build(),
                Lexeme.builder().type(LexemeType.SPACE).command(" ").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("h").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("o").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("r").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("a").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("r").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("i").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("o").build(),
                Lexeme.builder().type(LexemeType.UNEXPECTED).command("<").build(),
                Lexeme.builder().type(LexemeType.OPERATION).command("=").build(),
                Lexeme.builder().type(LexemeType.NUMBER).command("1").build(),
                Lexeme.builder().type(LexemeType.NUMBER).command("2").build(),
                Lexeme.builder().type(LexemeType.PARENTHESIS).command(")").build(),
                Lexeme.builder().type(LexemeType.SPACE).command(" ").build(),
                Lexeme.builder().type(LexemeType.CURLY_BRACKETS).command("{").build(),
                Lexeme.builder().type(LexemeType.PRINT).command("System.out.println").build(),
                Lexeme.builder().type(LexemeType.PARENTHESIS).command("(").build(),
                Lexeme.builder().type(LexemeType.STRING_FOUND).command("\"Bom dia\"").build(),
                Lexeme.builder().type(LexemeType.PARENTHESIS).command(")").build(),
                Lexeme.builder().type(LexemeType.SEMICOLON).command(";").build()),
                lexicalProcessor.interact(List.of("if (horario > 5 && horario <= 12) {System.out.println(\"Bom dia\");")));

}
