package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("T")); // only one letter
        assertFalse(Nric.isValidNric("Sxxxx456")); // missing last letter
        assertFalse(Nric.isValidNric("Axxxx456B")); // does not start with S or T
        assertFalse(Nric.isValidNric("Txxxx12A")); // missing a digit
        assertFalse(Nric.isValidNric("Txxxx1234A")); // too many digits
        assertFalse(Nric.isValidNric("Txxxx123!")); // contains special character

        // valid nric
        assertTrue(Nric.isValidNric("Sxxxx123A")); // starts with S
        assertTrue(Nric.isValidNric("Txxxx456Z")); // starts with T
    }

    @Test
    public void equals() {
        Nric nric = new Nric("Sxxxx123A");

        // same values -> returns true
        assertTrue(nric.equals(new Nric("Sxxxx123A")));

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different types -> returns false
        assertFalse(nric.equals(5.0f));

        // different values -> returns false
        assertFalse(nric.equals(new Nric("Txxxx123A")));
    }
}
