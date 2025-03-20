package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.person.Duty.DATE_PATTERN;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AssignCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final String currentMonthDateString =
            LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextMonthDateString =
            LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern(DATE_PATTERN));

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, null));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, "duty date"));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, currentMonthDateString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person assignedPerson =
                new PersonBuilder(personToAssign).withDuty(List.of(LocalDate.parse(currentMonthDateString))).build();

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_DUTY_SUCCESS,
                Messages.format(assignedPerson));

        expectedModel.setPerson(personToAssign, assignedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex, currentMonthDateString);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, currentMonthDateString);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person assignedPerson =
                new PersonBuilder(personToAssign).withDuty(List.of(LocalDate.parse(currentMonthDateString))).build();

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_DUTY_SUCCESS,
                Messages.format(assignedPerson));

        expectedModel.setPerson(personToAssign, assignedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex, currentMonthDateString);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignCommand assignFirstCommand = new AssignCommand(INDEX_FIRST_PERSON, currentMonthDateString);
        AssignCommand assignSecondCommand = new AssignCommand(INDEX_SECOND_PERSON, currentMonthDateString);
        AssignCommand assignDifferentDateCommand = new AssignCommand(INDEX_FIRST_PERSON, nextMonthDateString);
        // same object -> returns true
        assertTrue(assignFirstCommand.equals(assignFirstCommand));

        // same values -> returns true
        AssignCommand assignFirstCommandCopy = new AssignCommand(INDEX_FIRST_PERSON, currentMonthDateString);
        assertTrue(assignFirstCommand.equals(assignFirstCommandCopy));

        //different date -> returns false;
        assertFalse(assignFirstCommand.equals(assignDifferentDateCommand));
        assertFalse(assignSecondCommand.equals(assignDifferentDateCommand));

        // different types -> returns false
        assertFalse(assignFirstCommand.equals(1));

        // null -> returns false
        assertFalse(assignFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(assignFirstCommand.equals(assignSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        AssignCommand assignCommand = new AssignCommand(targetIndex, currentMonthDateString);
        String expected = AssignCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", dutyDate=" + currentMonthDateString + "}";

        assertEquals(expected, assignCommand.toString());
    }
}
