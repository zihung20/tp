package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SalaryTest {
    
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Salary(null));
    }

    @Test
    public void constructor_invalidSalary_throwsIllegalArgumentException() {
        int invalidSalary = 99;
        assertThrows(IllegalArgumentException.class, () -> new Salary(invalidSalary));
    }

    @Test
    public void isValidSalary() {
        // null salary number
        assertThrows(NullPointerException.class, () -> Salary.isValidSalary(null));

        // invalid salary numbers
        assertFalse(Salary.isValidSalary(0)); // 0 value
        assertFalse(Salary.isValidSalary(-100)); // negative value

        // valid Salary numbers
        assertTrue(Salary.isValidSalary(100)); // exactly SGD100
        assertTrue(Salary.isValidSalary(2500));
        assertTrue(Salary.isValidSalary(124293842033123)); // large salary numbers
    }

    @Test
    public void equals() {
        Salary Salary = new Salary(999);

        // same values -> returns true
        assertTrue(Salary.equals(new Salary(999)));

        // same object -> returns true
        assertTrue(Salary.equals(Salary));

        // null -> returns false
        assertFalse(Salary.equals(null));

        // different types -> returns false
        assertFalse(Salary.equals("999"));

        // different values -> returns false
        assertFalse(Salary.equals(new Salary(995)));
    }
}
