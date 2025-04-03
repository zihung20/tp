package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.person.Duty.DATE_PATTERN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ReassignCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
    private final LocalDate nextTwoMonthDate = LocalDate.now().plusMonths(2);
    private final String nextMonthDateString =
        nextMonthDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextTwoMonthDateString =
        nextTwoMonthDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String oldDutyDateFirstString = "2025-03-11";
    private final String oldDutyDateSecondString = "2025-01-05";
    private final LocalDate oldDutyDateFirst = LocalDate.parse(oldDutyDateFirstString);
    private final LocalDate oldDutyDateSecond = LocalDate.parse(oldDutyDateSecondString);
    private final String newDutyDateFirstString = nextMonthDateString;
    private final String newDutyDateSecondString = nextTwoMonthDateString;
    private final LocalDate newDutyDateFirst = LocalDate.parse(newDutyDateFirstString);
    private final LocalDate newDutyDateSecond = LocalDate.parse(newDutyDateSecondString);

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ReassignCommand(null, null, null));
        assertThrows(NullPointerException.class, () ->
                new ReassignCommand(null, "old duty date", null));
        assertThrows(NullPointerException.class, () ->
                new ReassignCommand(null, null, "new duty date"));
        assertThrows(NullPointerException.class, () ->
                new ReassignCommand(Index.fromOneBased(1), null, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToReassign = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());

        ReassignCommand reassignCommand = new ReassignCommand(INDEX_FOURTH_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyList = new ArrayList<>();
        dutyList.addAll(personToReassign.getDuty().getDutyList());
        dutyList.remove(oldDutyDateFirst);
        dutyList.add(newDutyDateFirst);
        Person reassignedPerson = new PersonBuilder(personToReassign).withDuty(dutyList).build();

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_SUCCESS,
                Messages.format(reassignedPerson));

        expectedModel.setPerson(personToReassign, reassignedPerson);
        assertCommandSuccess(reassignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexUnfilteredList_success() {
        Person personToReassignFirst = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person personToReassignSecond = model.getFilteredPersonList().get(INDEX_FIFTH_PERSON.getZeroBased());

        ReassignCommand reassignCommandFirst = new ReassignCommand(INDEX_FOURTH_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);
        ReassignCommand reassignCommandSecond = new ReassignCommand(INDEX_FIFTH_PERSON,
            oldDutyDateSecondString, newDutyDateSecondString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyListFirst = new ArrayList<>();
        List<LocalDate> dutyListSecond = new ArrayList<>();
        dutyListFirst.addAll(personToReassignFirst.getDuty().getDutyList());
        dutyListSecond.addAll(personToReassignSecond.getDuty().getDutyList());
        dutyListFirst.remove(oldDutyDateFirst);
        dutyListSecond.remove(oldDutyDateSecond);
        dutyListFirst.add(newDutyDateFirst);
        dutyListSecond.add(newDutyDateSecond);
        Person reassignedPersonFirst = new PersonBuilder(personToReassignFirst).withDuty(dutyListFirst).build();
        Person reassignedPersonSecond = new PersonBuilder(personToReassignSecond).withDuty(dutyListSecond).build();

        String expectedMessageFirst = String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_SUCCESS,
                Messages.format(reassignedPersonFirst));
        String expectedMessageSecond = String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_SUCCESS,
                Messages.format(reassignedPersonSecond));

        expectedModel.setPerson(personToReassignFirst, reassignedPersonFirst);
        assertCommandSuccess(reassignCommandFirst, model, expectedMessageFirst, expectedModel);

        expectedModel.setPerson(personToReassignSecond, reassignedPersonSecond);
        assertCommandSuccess(reassignCommandSecond, model, expectedMessageSecond, expectedModel);
    }

    @Test
    public void execute_validIndex_unfoundDate() {
        Person personToReassign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        //access internal date and remove existing date
        personToReassign.getDuty().getDutyList().remove(oldDutyDateFirst);

        ReassignCommand reassignCommand = new ReassignCommand(INDEX_FIRST_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);

        String expectedMessage =
                String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_FAILED_DUTY_NOT_FOUND,
                    Messages.format(personToReassign));

        assertCommandFailure(reassignCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ReassignCommand reassignCommand = new ReassignCommand(outOfBoundIndex,
            oldDutyDateFirstString, newDutyDateFirstString);

        assertCommandFailure(reassignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FOURTH_PERSON);

        Person personToReassign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ReassignCommand reassignCommand = new ReassignCommand(INDEX_FIRST_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyList = new ArrayList<>();
        dutyList.addAll(personToReassign.getDuty().getDutyList());
        dutyList.remove(oldDutyDateFirst);
        dutyList.add(newDutyDateFirst);
        Person reassignedPerson = new PersonBuilder(personToReassign).withDuty(dutyList).build();

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_SUCCESS,
                Messages.format(reassignedPerson));

        expectedModel.setPerson(personToReassign, reassignedPerson);

        assertCommandSuccess(reassignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_FOURTH_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ReassignCommand reassignCommand = new ReassignCommand(outOfBoundIndex, oldDutyDateFirstString,
            newDutyDateFirstString);

        assertCommandFailure(reassignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ReassignCommand reassignCommandFirst = new ReassignCommand(INDEX_FOURTH_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);
        ReassignCommand reassignCommandSecond = new ReassignCommand(INDEX_FIFTH_PERSON,
            oldDutyDateSecondString, newDutyDateSecondString);
        ReassignCommand reassignCommandDifferentDate = new ReassignCommand(INDEX_FOURTH_PERSON,
            oldDutyDateFirstString, newDutyDateSecondString);
        // same object -> returns true
        assertTrue(reassignCommandFirst.equals(reassignCommandFirst));

        // same values -> returns true
        ReassignCommand reassignCommandFirstCopy = new ReassignCommand(INDEX_FOURTH_PERSON,
            oldDutyDateFirstString, newDutyDateFirstString);
        assertTrue(reassignCommandFirst.equals(reassignCommandFirstCopy));

        //different date -> returns false;
        assertFalse(reassignCommandFirst.equals(reassignCommandDifferentDate));
        assertFalse(reassignCommandSecond.equals(reassignCommandDifferentDate));

        // different types -> returns false
        assertFalse(reassignCommandFirst.equals(1));

        // null -> returns false
        assertFalse(reassignCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(reassignCommandFirst.equals(reassignCommandSecond));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ReassignCommand reassignCommand = new ReassignCommand(targetIndex,
            oldDutyDateFirstString, newDutyDateFirstString);
        String expected = ReassignCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", oldDutyDate=" + oldDutyDateFirstString
                + ", newDutyDate=" + newDutyDateFirstString + "}";

        assertEquals(expected, reassignCommand.toString());
    }
}
