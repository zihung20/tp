package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Rank;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    @Override
    public AssignCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                    PREFIX_RANK, PREFIX_NAME, PREFIX_COMPANY, PREFIX_DUTY);
            Rank rank = ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
            String duty = ParserUtil.parseDuty(argMultimap.getValue(PREFIX_DUTY).get());

            return new AssignCommand(rank, name, company, duty);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), pe);
        }
    }
}
