package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DUTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DUTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUTY_AMY_STRING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.person.Duty;

public class AssignCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);
    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid indexes
        assertParseFailure(parser, "1 0" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // commas in indexes
        assertParseFailure(parser, "1, 2" + DUTY_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid duty date format
        assertParseFailure(parser, "1" + INVALID_DUTY_DESC, Duty.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_success() {
        List<Index> targetIndexList = List.of(INDEX_FIRST_PERSON);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + DUTY_DESC_AMY;
        AssignCommand expectedCommand = new AssignCommand(targetIndexList, VALID_DUTY_AMY_STRING);

        assertParseSuccess(parser, userInput, expectedCommand);

        targetIndexList = List.of(INDEX_SECOND_PERSON);
        String secondUserInput = INDEX_SECOND_PERSON.getOneBased() + DUTY_DESC_AMY;
        AssignCommand secondExpectedCommand = new AssignCommand(targetIndexList, VALID_DUTY_AMY_STRING);
        assertParseSuccess(parser, secondUserInput, secondExpectedCommand);
    }

    @Test
    public void parse_multiple_duty() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + DUTY_DESC_AMY + DUTY_DESC_BOB;

        //only record the last appearance of duty date
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DUTY));

        String secondUserInput = INDEX_SECOND_PERSON.getOneBased() + DUTY_DESC_BOB + DUTY_DESC_AMY;

        //only record the last appearance of duty date
        assertParseFailure(parser, secondUserInput,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DUTY));
    }
}
