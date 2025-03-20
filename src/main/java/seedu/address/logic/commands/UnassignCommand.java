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
 * Unassign a personnel duty date using the index specify in address book
 */
public class UnassignCommand extends Command {
    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_USAGE = String.format(
            "%s: unassign a personnel duty date by specifying the identity of the personnel. "
                    + "Duplicate dates will be considered the same.\n"
                    + "Parameters: INDEX, %sDUTY_DATE\n"
                    + "Example: unassign 1 d/2025-04-15",
            COMMAND_WORD, CliSyntax.PREFIX_DUTY);
    public static final String MESSAGE_UNASSIGN_DUTY_SUCCESS =
            "Success! Deleted personnel's duty %1$s!";
    public static final String MESSAGE_UNASSIGN_DUTY_FAILED =
            "Failed! The personnel's duty cannot found! %1$s";

    private final Index index;
    private final String dutyDate;

    /**
     * @param index the index of the personnel to unassign duty from
     * @param dutyDate the string representation of the duty date to unassign
     */
    public UnassignCommand(Index index, String dutyDate) {
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

        Person personToUnassign = lastShownList.get(index.getZeroBased());

        if (personToUnassign.unassignDuty(dutyDate)) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_UNASSIGN_DUTY_SUCCESS, Messages.format(personToUnassign)));
        } else {
            throw new CommandException(String.format(MESSAGE_UNASSIGN_DUTY_FAILED, Messages.format(personToUnassign)));
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignCommand temp)) {
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
