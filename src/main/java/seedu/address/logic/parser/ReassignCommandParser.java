package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.ReassignCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DUTY;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReassignCommand object
 */
public class ReassignCommandParser implements Parser<ReassignCommand> {
    
    @Override
    public ReassignCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DUTY, PREFIX_NEW_DUTY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReassignCommand.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DUTY, PREFIX_NEW_DUTY);

        if (argMultimap.getValue(PREFIX_DUTY).isPresent()
                && argMultimap.getValue(PREFIX_NEW_DUTY).isPresent()) {
            String oldDutyDate = ParserUtil.parseDuty(argMultimap.getValue(PREFIX_DUTY).get());
            String newDutyDate = ParserUtil.parseDuty(argMultimap.getValue(PREFIX_NEW_DUTY).get());
            return new ReassignCommand(index, oldDutyDate, newDutyDate);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReassignCommand.MESSAGE_USAGE));
        }
    }
}
