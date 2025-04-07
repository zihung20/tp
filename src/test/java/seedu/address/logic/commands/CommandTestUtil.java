package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Duty;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "80000000";
    public static final String VALID_PHONE_BOB = "99999999";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_NRIC_AMY = "Txxxx123A";
    public static final String VALID_NRIC_BOB = "Sxxxx123B";
    public static final String VALID_SALARY_AMY = "1500";
    public static final String VALID_SALARY_BOB = "700";
    public static final String VALID_COMPANY_AMY = "Alpha";
    public static final String VALID_COMPANY_BOB = "Bravo";
    public static final String VALID_RANK_AMY = "1SG";
    public static final String VALID_RANK_BOB = "PTE";
    public static final List<LocalDate> VALID_DUTY_AMY =
            new ArrayList<>(Arrays.asList(LocalDate.now()));
    public static final List<LocalDate> VALID_DUTY_BOB =
            new ArrayList<>(Arrays.asList(LocalDate.now().plusMonths(1)));
    public static final String VALID_DUTY_AMY_STRING =
            LocalDate.now().format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN));
    public static final String VALID_DUTY_BOB_STRING =
            LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN));

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String SALARY_DESC_AMY = " " + PREFIX_SALARY + VALID_SALARY_AMY;
    public static final String SALARY_DESC_BOB = " " + PREFIX_SALARY + VALID_SALARY_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String RANK_DESC_AMY = " " + PREFIX_RANK + VALID_RANK_AMY;
    public static final String RANK_DESC_BOB = " " + PREFIX_RANK + VALID_RANK_BOB;
    public static final String DUTY_DESC_AMY = " " + PREFIX_DUTY + VALID_DUTY_AMY_STRING;
    public static final String DUTY_DESC_BOB = " " + PREFIX_DUTY + VALID_DUTY_BOB_STRING;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "Axxxx123A"; // 'A' not allowed in NRIC starting
    public static final String INVALID_SALARY_DESC = " " + PREFIX_SALARY + "50"; // Salary must be at least 100
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "0123"; // Company cannot have numbers
    public static final String INVALID_RANK_DESC = " " + PREFIX_RANK + "ABCDE"; // Rank can only have 2-4 letters
    public static final String INVALID_DUTY_DESC = " " + PREFIX_DUTY + "26/11/2025"; // valid format is yyyy-mm-dd

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withNric(VALID_NRIC_AMY).withSalary(VALID_SALARY_AMY)
                .withCompany(VALID_COMPANY_AMY).withRank(VALID_RANK_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withNric(VALID_NRIC_BOB).withSalary(VALID_SALARY_BOB)
                .withCompany(VALID_COMPANY_BOB).withRank(VALID_RANK_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
