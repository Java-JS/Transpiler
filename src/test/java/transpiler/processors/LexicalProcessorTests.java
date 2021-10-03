package transpiler.processors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LexicalProcessorTests {
    LexicalProcessor lexicalProcessor = new LexicalProcessor();

    @Test
    void helloWorld() {
        Assertions.assertEquals(List.of("System.out.println",
                        "(",
                        "\"Hello World!\"",
                        ")",
                        ";"),
                lexicalProcessor.interactOverLine("System.out.println(\"Hello World!\");"));

        Assertions.assertEquals(List.of("[FRAG: print=System.out.println]",
                        "[FRAG: parenthesis=(]",
                        "[FRAG: String found=\"Hello World!\"]",
                        "[FRAG: parenthesis=)]",
                        "[FRAG: semicolon=;]"),
                lexicalProcessor.interact(List.of("System.out.println(\"Hello World!\");")));
    }
}
