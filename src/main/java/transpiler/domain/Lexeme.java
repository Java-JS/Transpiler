package transpiler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import transpiler.enumerator.LexemeType;

@Data
@Builder
@AllArgsConstructor
public class Lexeme {
    LexemeType type;
    String command;

    @Override
    public String toString() {
        return "Lexeme[" + type.getType() + ", " + command + ']';
    }
}
