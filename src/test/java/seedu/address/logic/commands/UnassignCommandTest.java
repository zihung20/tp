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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnassignCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LocalDate currentMonthDate = LocalDate.now();
    private final LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
    private final String currentMonthDateString =
            currentMonthDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextMonthDateString =
            nextMonthDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new UnassignCommand(null, null));
        assertThrows(NullPointerException.class, () ->
                new UnassignCommand(null, "duty date"));
        assertThrows(NullPointerException.class, () ->
                new UnassignCommand(Index.fromOneBased(1), null));
    }

    @Test
    @Disabled
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnassign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        //access internal data to add duty
        personToUnassign.getDuty().getDutyList().add(currentMonthDate);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_PERSON, currentMonthDateString);

        String expectedMessage = String.format(UnassignCommand.MESSAGE_UNASSIGN_DUTY_SUCCESS,
                Messages.format(personToUnassign));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person temp = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        temp.unassignDuty(currentMonthDateString);

        assertCommandSuccess(unassignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndex_unfoundDate() {
        Person personToUnassign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        //access internal date and remove existing date
        personToUnassign.getDuty().getDutyList().remove(currentMonthDate);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_PERSON, currentMonthDateString);

        String expectedMessage =
                String.format(UnassignCommand.MESSAGE_UNASSIGN_DUTY_FAILED, Messages.format(personToUnassign));
        assertCommandFailure(unassignCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignCommand unassignCommand = new UnassignCommand(outOfBoundIndex, currentMonthDateString);

        assertCommandFailure(unassignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, currentMonthDateString);

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_DUTY_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person temp = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        temp.assignDuty(currentMonthDateString);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignCommand unassignCommand = new UnassignCommand(outOfBoundIndex, currentMonthDateString);

        assertCommandFailure(unassignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnassignCommand unassignCommandFirst = new UnassignCommand(INDEX_FIRST_PERSON, currentMonthDateString);
        UnassignCommand unassignCommandSecond = new UnassignCommand(INDEX_SECOND_PERSON, currentMonthDateString);
        UnassignCommand unassignCommandDifferentDate = new UnassignCommand(INDEX_FIRST_PERSON, nextMonthDateString);
        // same object -> returns true
        assertTrue(unassignCommandFirst.equals(unassignCommandFirst));

        // same values -> returns true
        UnassignCommand unassignCommandFirstCopy = new UnassignCommand(INDEX_FIRST_PERSON, currentMonthDateString);
        assertTrue(unassignCommandFirst.equals(unassignCommandFirstCopy));

        //different date -> returns false;
        assertFalse(unassignCommandFirst.equals(unassignCommandDifferentDate));
        assertFalse(unassignCommandSecond.equals(unassignCommandDifferentDate));

        // different types -> returns false
        assertFalse(unassignCommandFirst.equals(1));

        // null -> returns false
        assertFalse(unassignCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(unassignCommandFirst.equals(unassignCommandSecond));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnassignCommand unassignCommand = new UnassignCommand(targetIndex, currentMonthDateString);
        String expected = UnassignCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", dutyDate=" + currentMonthDateString + "}";

        assertEquals(expected, unassignCommand.toString());
    }
}
