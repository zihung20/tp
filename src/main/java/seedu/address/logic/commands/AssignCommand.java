package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;

/**
 * currently only workable for no duplicate entry
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
            "Success! Duty assigned to person%1$s!";
    public static final String MESSAGE_INVALID_PERSON = "Error! Personnel not found!";

    private final Rank rank;
    private final Name name;
    private final Company company;
    private final String dutyDate;

    /**
     * @param rank the rank of the personnel
     * @param name the name of the peronnel
     * @param company the company of ther personnel
     * @param duty the {@code String} value of the duty date
     */
    public AssignCommand(Rank rank, Name name, Company company, String duty) {
        requireAllNonNull(rank, name, company, duty);

        this.rank = rank;
        this.name = name;
        this.company = company;
        this.dutyDate = duty;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        FilteredList<Person> persons = model.getAddressBook().getPersonList()
                .filtered(person -> person.isSamePerson(rank, name, company));

        if (persons.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        Person personToAssign = persons.get(0);
        personToAssign.assignDuty(dutyDate);
        return new CommandResult(String.format(MESSAGE_ASSIGN_DUTY_SUCCESS, Messages.format(personToAssign)));
    }
}
