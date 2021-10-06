package transpiler.processors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void testHelloWorldFragments(){
        Assertions.assertEquals(List.of("[FRAG: print=System.out.println]",
                        "[FRAG: parenthesis=(]",
                        "[FRAG: String found=\"Hello World!\"]",
                        "[FRAG: parenthesis=)]",
                        "[FRAG: semicolon=;]"),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\");")));
    }

    @Test
    void testStringWithPlusSymbol(){
        Assertions.assertEquals(List.of("[FRAG: print=System.out.println]",
                        "[FRAG: parenthesis=(]",
                        "[FRAG: String found=\"Hello World!\"]",
                        "[FRAG: operation=+]",
                        "[FRAG: String found=\"random\"]",
                        "[FRAG: parenthesis=)]",
                        "[FRAG: semicolon=;]"),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\" + \"random\");")));
    }
}
