package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingPrefix_failure() {
        // No prefix at all
        assertParseFailure(parser, "Alpha Bravo", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingArguments_failure() {
        // Only prefix, no company
        assertParseFailure(parser, "c/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid preamble before prefix
        assertParseFailure(parser, "invalid c/Alpha", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_success() {
        String userInput = " c/Alpha Bravo";
        FilterCommand expectedCommand =
                new FilterCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Alpha", "Bravo")));

        System.out.println(userInput);
        System.out.println(expectedCommand);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
