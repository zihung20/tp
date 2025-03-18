package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
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
                    + "Parameters: INDEX, %sDUTY_DATE\n"
                    + "Example: assign 1 d/2025-04-15",
            COMMAND_WORD, CliSyntax.PREFIX_DUTY);
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand temp)) {
            return false;
        } else {
            return index.equals(temp.index) && dutyDate.equals(temp.dutyDate);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("dutyDate", dutyDate)
                .toString();
    }
}
