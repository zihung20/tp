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
 * Changes the assigned duty date to a new duty date.
 */
public class ReassignCommand extends Command {
    public static final String COMMAND_WORD = "reassign";
    public static final String MESSAGE_USAGE = String.format(
        "%s: Reassign a duty date to personnel by specifying the identity of the personnel. "
                    + "Duplicate dates will be considered the same.\n"
                    + "Old duty date shouldn't be the same as new duty date.\n"
                    + "Parameters: INDEX, %sDUTY_DATE_TO_BE_CHANGED, %sNEW_DUTY_DATE\n"
                    + "Example: reassign 1 d/2025-04-15 nd/2025-04-26",
            COMMAND_WORD, CliSyntax.PREFIX_DUTY, CliSyntax.PREFIX_NEW_DUTY);
    public static final String MESSAGE_REASSIGN_DUTY_SUCCESS = 
        "Success! Duty reassigned to person %1$s!";
    public static final String MESSAGE_REASSIGN_DUTY_FAILED_DUTY_NOT_FOUND =
        "Failed! The personnel's old duty cannot be found! %1$s";
    public static final String MESSAGE_REASSIGN_DUTY_FAILED_SAME_DATE =
        "Failed! The old duty date shouldn't be the same as the new duty date! %1$s";

    private final Index index;
    private final String oldDutyDate;
    private final String newDutyDate;

    /**
     * @param index The index of the personnel to reassign duty
     * @param oldDutyDate The string representation of the old duty date to be replaced
     * @param newDutyDate The string representation of the new duty date to replace
     */
    public ReassignCommand(Index index, String oldDutyDate, String newDutyDate) {
        requireAllNonNull(index, oldDutyDate, newDutyDate);

        this.index = index;
        this.oldDutyDate = oldDutyDate;
        this.newDutyDate = newDutyDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToReassign = lastShownList.get(index.getZeroBased());

        if (oldDutyDate.equals(newDutyDate)) {
            throw new CommandException(String.format(MESSAGE_REASSIGN_DUTY_FAILED_SAME_DATE, Messages.format(personToReassign)));
        }

        if (personToReassign.unassignDuty(oldDutyDate)) {
            personToReassign.assignDuty(newDutyDate);
            return new CommandResult(String.format(MESSAGE_REASSIGN_DUTY_SUCCESS, Messages.format(personToReassign)));
        } else {
            throw new CommandException(String.format(MESSAGE_REASSIGN_DUTY_FAILED_DUTY_NOT_FOUND,
            Messages.format(personToReassign)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReassignCommand temp)) {
            return false;
        } else {
            return index.equals(temp.index) && oldDutyDate.equals(temp.oldDutyDate)
                    && newDutyDate.equals(temp.newDutyDate);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("oldDutyDate", oldDutyDate)
                .add("newDutyDate", newDutyDate)
                .toString();
    }
}
