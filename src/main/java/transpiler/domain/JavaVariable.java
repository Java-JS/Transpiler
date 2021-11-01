package transpiler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JavaVariable {
    private String name;
    private String type;
    private String allocation;
}
