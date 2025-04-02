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
    public static final String MESSAGE_USAGE = String.format("%s: Assign a duty date to personnel(s) identified "
                    + "by the index number used in the displayed person list. "
                    + "Duplicate dates will be considered the same.\n"
                    + "Parameters: INDEX(S), %sDUTY_DATE\n"
                    + "Example: assign 1 d/2025-04-15\n"
                    + "Example: assign 1 2 3 d/2025-04-15",
            COMMAND_WORD, CliSyntax.PREFIX_DUTY);
    public static final String MESSAGE_ASSIGN_DUTY_SUCCESS = "Success! Duty assigned to person %1$s!";
    public static final String MESSAGE_MASS_ASSIGN_DUTY_SUCCESS = "Success! Duty assigned to %1$s persons!";

    private final List<Index> indexList;
    private final String dutyDate;

    /**
     * @param indexList The index of the personnel to assign duty
     * @param dutyDate The string representation of the duty dates
     */
    public AssignCommand(List<Index> indexList, String dutyDate) {
        requireAllNonNull(indexList, dutyDate);

        this.indexList = indexList;
        this.dutyDate = dutyDate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indexList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : indexList) {
            lastShownList = model.getFilteredPersonList();
            Person personToAssign = lastShownList.get(index.getZeroBased());
            model.assignDutyToPerson(personToAssign, dutyDate);
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        if (indexList.size() > 1) {
            long size = indexList.stream()
                    .map(Index::getOneBased)
                    .distinct()
                    .count();
            return new CommandResult(String.format(MESSAGE_MASS_ASSIGN_DUTY_SUCCESS, size));
        }

        Person personToAssign = lastShownList.get(indexList.get(0).getZeroBased());
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
            return indexList.equals(temp.indexList) && dutyDate.equals(temp.dutyDate);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", indexList.toString())
                .add("dutyDate", dutyDate)
                .toString();
    }
}
