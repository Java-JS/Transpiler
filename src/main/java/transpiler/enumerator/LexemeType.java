package transpiler.enumerator;

public enum LexemeType {
    UNEXPECTED("unexpected"),
    CLASS("class"),
    MAIN_METHOD("main_method"),
    PARENTHESIS("parenthesis"),
    CURLY_BRACKETS("curly_brackets"),
    SQUARE_BRACKETS("square_brackets"),
    PRINT("print"),
    SEMICOLON("semicolon"),
    NUMBER("number"),
    COMMAND("command"),
    TYPE("type"),
    VOID("void"),
    SPACE("space"),
    POINT("point"),
    OPERATION("operation"),
    ACCESSOR_MODIFIER("accessor_modifier"),
    STRING_FOUND("string_found"),
    VARIABLE("variable");

    private final String lexemeType;

    LexemeType(String lexemeType)  {
        this.lexemeType = lexemeType;
    }

    public String getLexemeType() {
        return lexemeType;
    }
}
