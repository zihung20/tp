package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }


    @Test
    public void isValidAddress() {
        // valid addresses with #, although not appropriate
        assertTrue(Address.isValidAddress("123 Main St ##"));
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        //valid address with - only, although not appropriate
        assertTrue(Address.isValidAddress("-")); // one character
        // address with parentheses and hashtag
        assertTrue(Address.isValidAddress("Apt (4B) #202"));
        // address with . and ,
        assertTrue(Address.isValidAddress("123 Main St. Street, Apt 4B"));
        // address with numbers, spaces, parentheses
        assertTrue(Address.isValidAddress("42nd Ave, NYC (Unit 3)"));
        // address with ' and -
        assertTrue(Address.isValidAddress("123 #Street's Name, Building 2-A"));
    }

    @Test
    public void invalidAddressTest() {
        // null address
        assertFalse(Address.isValidAddress(null));
        // empty string
        assertFalse(Address.isValidAddress(""));
        // spaces only
        assertFalse(Address.isValidAddress(" "));
        // invalid special characters
        assertFalse(Address.isValidAddress("valid address invalid symbol @"));
        // invalid special character ;
        assertFalse(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879"));
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));

        // same address but with different case - address is case-sensitive
        assertFalse(address.equals(new Address("valid address")));

        // same address but with different whitespace -> returns false (if whitespace matters)
        assertFalse(address.equals(new Address("  Valid Address ")));
    }
}
