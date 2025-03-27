package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SALARY = new Prefix("s/");
    public static final Prefix PREFIX_DUTY = new Prefix("d/");
    public static final Prefix PREFIX_NEW_DUTY = new Prefix("/nd");
    public static final Prefix PREFIX_NRIC = new Prefix("nr/");
    public static final Prefix PREFIX_COMPANY = new Prefix("c/");
    public static final Prefix PREFIX_RANK = new Prefix("r/");
}
