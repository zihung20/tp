package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUTY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnassignCommand object
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    @Override
    public UnassignCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DUTY);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignCommand.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DUTY);

        if (argMultimap.getValue(PREFIX_DUTY).isPresent()) {
            String dutyDate = ParserUtil.parseDuty(argMultimap.getValue(PREFIX_DUTY).get());
            return new UnassignCommand(index, dutyDate);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        }
    }
}
