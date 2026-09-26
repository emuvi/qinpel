package br.com.pointel.wiz_jarch.flow;

/**
 * Represents a boundary condition used by {@link DatexNode} to figure out
 * where data extraction begins and ends.
 */
public class DatexToken {
    
    /**
     * Helper to wrap multiple tokens into an array.
     *
     * @param tokens the tokens to wrap
     * @return an array of the given tokens
     */
    public static DatexToken[] of(DatexToken... tokens){
        return tokens;
    }

    /**
     * Creates a token matching the absolute beginning of a text block.
     *
     * @return a DatexToken of kind TextBegin
     */
    public static DatexToken textBegin() {
        return new DatexToken(Kind.TextBegin, null);
    }

    /**
     * Creates a token matching a literal exact string.
     *
     * @param code the exact string to match
     * @return a DatexToken of kind Literal
     */
    public static DatexToken literal(String code) {
        return new DatexToken(Kind.Literal, code);
    }

    /**
     * Creates a token matching a Regular Expression pattern.
     *
     * @param code the regex pattern
     * @return a DatexToken of kind Regex
     */
    public static DatexToken regex(String code) {
        return new DatexToken(Kind.Regex, code);
    }

    /**
     * Creates a token matching the absolute end of a text block.
     *
     * @return a DatexToken of kind TextEnd
     */
    public static DatexToken textEnd() {
        return new DatexToken(Kind.TextEnd, null);
    }


    /**
     * Creates an array of tokens matching multiple literal exact strings.
     *
     * @param codes the exact strings to match
     * @return an array of DatexTokens of kind Literal
     */
    public static DatexToken[] literals(String... codes) {
        DatexToken[] tokens = new DatexToken[codes.length];
        for (int i = 0; i < codes.length; i++) {
            tokens[i] = literal(codes[i]);
        }
        return tokens;
    }

    /**
     * Creates an array of tokens matching multiple Regular Expression patterns.
     *
     * @param codes the regex patterns to match
     * @return an array of DatexTokens of kind Regex
     */
    public static DatexToken[] regexes(String... codes) {
        DatexToken[] tokens = new DatexToken[codes.length];
        for (int i = 0; i < codes.length; i++) {
            tokens[i] = regex(codes[i]);
        }
        return tokens;
    }

    /**
     * Creates a token matching common whitespace characters.
     *
     * @return a DatexToken matching \s+
     */
    public static DatexToken whitespace() {
        return regex("\\s+");
    }

    /**
     * Creates a token matching common new line characters.
     *
     * @return a DatexToken matching \r?\n
     */
    public static DatexToken newline() {
        return regex("\\r?\\n");
    }

    /**
     * Creates a token matching word characters (a-z, A-Z, 0-9, _).
     *
     * @return a DatexToken matching \w+
     */
    public static DatexToken word() {
        return regex("\\w+");
    }

    private final Kind kind;
    private final String code;

    /**
     * Constructs a new DatexToken.
     *
     * @param kind the evaluation strategy kind
     * @param code the matching string or pattern (can be null for boundary tokens)
     */
    public DatexToken(Kind kind, String code) {
        this.kind = kind;
        this.code = code;
    }

    /**
     * @return the evaluation strategy kind of this token
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * @return the matching string or pattern of this token
     */
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "DatexToken{" + "kind=" + kind + ", code='" + code + '\'' + '}';
    }

    /**
     * The evaluation strategies available for matching a token.
     */
    public enum Kind {
        TextBegin, Literal, Regex, TextEnd
    }

}
