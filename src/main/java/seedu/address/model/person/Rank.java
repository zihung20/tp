package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rank in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRank(String)}
 */
public class Rank {

    public static final String MESSAGE_CONSTRAINTS =
            "Rank should contain exactly 2-4 uppercase alphanumeric characters, and it should not be blank (e.g., CPL)";

    // The rank must contain 2-4 alphanumeric characters.
    public static final String VALIDATION_REGEX = "[A-Z0-9]{2,4}";

    public final String fullRank;

    /**
     * Constructs a {@code Rank}.
     *
     * @param rank A valid rank.
     */
    public Rank(String rank) {
        requireNonNull(rank);
        checkArgument(isValidRank(rank), MESSAGE_CONSTRAINTS);
        fullRank = rank;
    }

    /**
     * Returns true if a given string is a valid rank.
     */
    public static boolean isValidRank(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullRank;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rank)) {
            return false;
        }

        Rank otherRank = (Rank) other;
        return fullRank.equals(otherRank.fullRank);
    }

    @Override
    public int hashCode() {
        return fullRank.hashCode();
    }

}
