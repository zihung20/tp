package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names must not be blank and can contain at most one of each special character:\n"
                + "hyphen (-), slash (/), or at symbol (@).";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z-/@][a-zA-Z\\s-/@]*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        // Check for occurrences of special characters
        int hyphenCount = test.split("-", -1).length - 1;
        int slashCount = test.split("/", -1).length - 1;
        int atCount = test.split("@", -1).length - 1;

        return hyphenCount <= 1 && slashCount <= 1 && atCount <= 1;
    }

    /**
     * Checks if the given keyword is contained within the full name,
     * ignoring case differences.
     *
     * @param keyword the keyword to search for in the full name
     * @return {@code true} if the full name contains the keyword (case-insensitive), {@code false} otherwise
     */
    public boolean isCaseInsensitveSameName(String keyword) {
        return fullName.toLowerCase().contains(keyword);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
