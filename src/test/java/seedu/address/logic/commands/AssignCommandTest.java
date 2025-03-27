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

public class AssignCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final String currentMonthDateString =
            LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextMonthDateString =
            LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextTwoMonthDateString =
            LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern(DATE_PATTERN));

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, null));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, "duty date"));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(List.of(Index.fromOneBased(1)), null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        List<Index> indexList = List.of(INDEX_FIRST_PERSON);
        AssignCommand assignCommand = new AssignCommand(indexList, currentMonthDateString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyList = new ArrayList<>();
        dutyList.addAll(personToAssign.getDuty().getDutyList());
        dutyList.add(LocalDate.parse(currentMonthDateString));
        Person assignedPerson = new PersonBuilder(personToAssign).withDuty(dutyList).build();

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_DUTY_SUCCESS,
                Messages.format(assignedPerson));

        expectedModel.setPerson(personToAssign, assignedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexUnfilteredList_success() {
        Person personToAssignFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToAssignSecond = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        List<Index> indexList = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        AssignCommand assignCommand = new AssignCommand(indexList, nextTwoMonthDateString);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyListFirst = new ArrayList<>();
        List<LocalDate> dutyListSecond = new ArrayList<>();
        dutyListFirst.addAll(personToAssignFirst.getDuty().getDutyList());
        dutyListSecond.addAll(personToAssignSecond.getDuty().getDutyList());
        dutyListFirst.add(LocalDate.parse(nextTwoMonthDateString));
        dutyListSecond.add(LocalDate.parse(nextTwoMonthDateString));
        Person assignedPersonFirst = new PersonBuilder(personToAssignFirst).withDuty(dutyListFirst).build();
        Person assignedPersonSecond = new PersonBuilder(personToAssignSecond).withDuty(dutyListSecond).build();

        String expectedMessage = String.format(AssignCommand.MESSAGE_MASS_ASSIGN_DUTY_SUCCESS,
                indexList.size());

        expectedModel.setPerson(personToAssignFirst, assignedPersonFirst);
        expectedModel.setPerson(personToAssignSecond, assignedPersonSecond);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexList = List.of(outOfBoundIndex);
        AssignCommand assignCommand = new AssignCommand(indexList, currentMonthDateString);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        List<Index> indexList = List.of(INDEX_FIRST_PERSON);
        AssignCommand assignCommand = new AssignCommand(indexList, nextMonthDateString);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyList = new ArrayList<>();
        dutyList.addAll(personToAssign.getDuty().getDutyList());
        dutyList.add(LocalDate.parse(nextMonthDateString));
        Person assignedPerson = new PersonBuilder(personToAssign).withDuty(dutyList).build();

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

        List<Index> indexList = List.of(outOfBoundIndex);
        AssignCommand assignCommand = new AssignCommand(indexList, currentMonthDateString);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignCommand assignFirstCommand = new AssignCommand(List.of(INDEX_FIRST_PERSON), currentMonthDateString);
        AssignCommand assignSecondCommand = new AssignCommand(List.of(INDEX_SECOND_PERSON), currentMonthDateString);
        AssignCommand assignDifferentDateCommand = new AssignCommand(List.of(INDEX_FIRST_PERSON), nextMonthDateString);
        // same object -> returns true
        assertTrue(assignFirstCommand.equals(assignFirstCommand));

        // same values -> returns true
        AssignCommand assignFirstCommandCopy = new AssignCommand(List.of(INDEX_FIRST_PERSON), currentMonthDateString);
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
        List<Index> indexList = List.of(targetIndex);
        AssignCommand assignCommand = new AssignCommand(indexList, currentMonthDateString);
        String expected = AssignCommand.class.getCanonicalName()
                + "{targetIndex=" + indexList
                + ", dutyDate=" + currentMonthDateString + "}";

        assertEquals(expected, assignCommand.toString());
    }
}
