package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.person.Duty.DATE_PATTERN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
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
    private final String currentMonthDateString =
            LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    private final String nextMonthDateString =
            LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern(DATE_PATTERN));

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
    @Disabled
    public void execute_validIndexUnfilteredList_success() {
        Person personToReassign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String oldDutyDate = currentMonthDateString;
        String newDutyDate = "2025-03-20";
        ReassignCommand reassignCommand = new ReassignCommand(INDEX_FIRST_PERSON, oldDutyDate, newDutyDate);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<LocalDate> dutyList = new ArrayList<>();
        dutyList.addAll(personToReassign.getDuty().getDutyList());
        dutyList.add(LocalDate.parse(newDutyDate));
        Person reassignedPerson = new PersonBuilder(personToReassign).withDuty(dutyList).build();

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_DUTY_SUCCESS,
                Messages.format(reassignedPerson));

        expectedModel.setPerson(personToReassign, reassignedPerson);

        assertCommandSuccess(reassignCommand, model, expectedMessage, expectedModel);
    }
}
