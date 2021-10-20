package transpiler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Lexeme {
    String type;
    String command;

    @Override
    public String toString() {
        return "Lexeme[" + type + ", " + command + ']';
    }
}
