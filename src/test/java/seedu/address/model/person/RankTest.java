package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RankTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rank(null));
    }

    @Test
    public void constructor_invalidRank_throwsIllegalArgumentException() {
        String invalidRank = "";
        assertThrows(IllegalArgumentException.class, () -> new Rank(invalidRank));
    }

    @Test
    public void isValidRank() {
        // null rank
        assertThrows(NullPointerException.class, () -> Rank.isValidRank(null));

        // invalid rank
        assertFalse(Rank.isValidRank("")); // empty string
        assertFalse(Rank.isValidRank(" ")); // spaces only
        assertFalse(Rank.isValidRank("^")); // only non-alphanumeric characters
        assertFalse(Rank.isValidRank("SG*")); // contains non-alphanumeric characters
        assertFalse(Rank.isValidRank("3SGT")); // More than 3 characters (invalid)
        assertFalse(Rank.isValidRank("S")); // Less than 3 characters (invalid)

        // valid rank
        assertTrue(Rank.isValidRank("CPL")); // alphabets only
        assertTrue(Rank.isValidRank("123")); // numbers only
        assertTrue(Rank.isValidRank("3SG")); // alphanumeric characters
    }

    @Test
    public void equals() {
        Rank rank = new Rank("CPL"); // Changed from "Valid Rank" to a valid rank

        // same values -> returns true
        assertTrue(rank.equals(new Rank("CPL")));

        // same object -> returns true
        assertTrue(rank.equals(rank));

        // null -> returns false
        assertFalse(rank.equals(null));

        // different types -> returns false
        assertFalse(rank.equals(5.0f));

        // different values -> returns false
        assertFalse(rank.equals(new Rank("SGT"))); // Different valid rank
    }
}
