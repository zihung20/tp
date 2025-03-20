package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(int)}
 */
public class Salary {
    public static final String MESSAGE_CONSTRAINTS =
            "Salary should only contain numbers, and it should be SGD100 - SGD9999.";
    public static final String VALIDATION_REGEX = "^(?:[1-9][0-9]{2,3})$"; // Only accepts value between 100-9999
    public final int value;

    /**
     * Consturcts a {@code Salary}
     *
     * @param salary A valid salary wage.
     */
    public Salary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(salary);
    }

    /**
     * Returns true if a given int is a valid salary.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(VALIDATION_REGEX);
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
