package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Assign a duty to an existing personnel in address book
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_USAGE = String.format(
            "%s: Assign a duty date to personnel by specifying the identity of the personnel. "
                    + "Duplicate dates will be considered the same.\n"
                    + "Parameters: %sRANK (all caps), %sNAME, %sCOMPANY, %sDUTY_DATE\n"
                    + "Example: assign r/3SG, n/Alice, c/Alpha, d/2025-04-15",
            COMMAND_WORD, CliSyntax.PREFIX_RANK, CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_COMPANY, CliSyntax.PREFIX_DUTY);
    public static final String MESSAGE_ASSIGN_DUTY_SUCCESS =
            "Success! Duty assigned to person %1$s!";

    private final Index index;
    private final String dutyDate;

    /**
     * @param index the index of the personnel to assign duty
     * @param dutyDate the string representation of the duty dates
     */
    public AssignCommand(Index index, String dutyDate) {
        requireAllNonNull(index, dutyDate);

        this.index = index;
        this.dutyDate = dutyDate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(index.getZeroBased());
        personToAssign.assignDuty(dutyDate);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ASSIGN_DUTY_SUCCESS, Messages.format(personToAssign)));
    }
}
