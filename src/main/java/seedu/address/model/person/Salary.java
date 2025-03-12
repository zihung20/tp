package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(int)}
 */
public class Salary {
    public static final String MESSAGE_CONSTRAINTS =
            "Salary should only contain numbers, and it should be >SGD100.";
    public final int value;

    /**
     * Consturcts a {@code Salary}
     * 
     * @param salary A valid salary wage.
     */
    public Salary(int salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), MESSAGE_CONSTRAINTS);
        value = salary;
    }

    /**
     * Returns true if a given int is a valid salary.
     */
    public static boolean isValidSalary(int test) {
        return test >= 100;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Salary)) {
            return false;
        }

        Salary otherSalary = (Salary) other;
        return value == otherSalary.value; 
    }
}
