package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Company;
import seedu.address.model.person.Name;

public class AssignCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, new Name("name"), null, null));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, null, new Company("company"), null));
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, null, null, "duty date"));
    }


}
