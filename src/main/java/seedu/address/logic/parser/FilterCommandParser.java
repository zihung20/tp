package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;

import java.util.Arrays;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMPANY);

        System.out.println("DEBUG - Preamble: '" + argMultimap.getPreamble() + "'");
        System.out.println("DEBUG - Company Value Present: " + argMultimap.getValue(PREFIX_COMPANY).isPresent());
        System.out.println("DEBUG - Company Value: '" + argMultimap.getValue(PREFIX_COMPANY).orElse("null") + "'");


        if (!argMultimap.getValue(PREFIX_COMPANY).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String companyArgs = argMultimap.getValue(PREFIX_COMPANY).get();
        String[] companyKeywords = companyArgs.trim().split("\\s+");

        return new FilterCommand(new CompanyContainsKeywordsPredicate(Arrays.asList(companyKeywords)));
    }
}
