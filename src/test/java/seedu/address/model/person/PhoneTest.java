package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));
    }

    @Test
    public void validPhoneNumber() {
        // valid phone numbers
        assertTrue(Phone.isValidPhone("80000000"));
        assertTrue(Phone.isValidPhone("91234567"));
        assertTrue(Phone.isValidPhone("89999999"));
        assertTrue(Phone.isValidPhone("99999999"));
    }

    @Test
    public void invalidPhoneNumber() {
        //some of the test cases generate by ChatGPT
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 8 digits
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("71234567")); // doesn't start with 8 or 9
        assertFalse(Phone.isValidPhone("12345678")); // doesn't start with 8 or 9
        assertFalse(Phone.isValidPhone("9912345")); // only 7 digits
        assertFalse(Phone.isValidPhone("999999999")); // 9 digits
    }

    @Test
    public void equals() {
        String validPhone = "84588016";
        String validPhone2 = "84588017";
        Phone phone = new Phone(validPhone);

        // same values -> returns true
        assertTrue(phone.equals(new Phone(validPhone)));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone(validPhone2)));
    }
}
