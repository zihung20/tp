package seedu.address.logic.commands.exceptions;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * View the assigned duties of an existing personnel in address book
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = String.format(
            "%s: View the duty date(s) of a personnel by specifying the index of the personnel. "
                    + "Parameters: INDEX\n"
                    + "Example: view 1",
            COMMAND_WORD);
    public static final String MESSAGE_VIEW_SUCCESS =
            "Success! Personnel duty found! Their duty date(s) are located at the bottom right side!";

    private final Index index;

    /**
     * @param index The index of the personnel to find
     */
    public ViewCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(index.getZeroBased());
        model.viewPerson(personToView);

        return new CommandResult(MESSAGE_VIEW_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand temp)) {
            return false;
        } else {
            return index.equals(temp.index);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
