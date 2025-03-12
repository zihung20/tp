package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company name
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid company name
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany("^")); // only non-alphanumeric characters
        assertFalse(Company.isValidCompany("Charlie*")); // contains non-alphanumeric characters

        // valid company name
        assertTrue(Company.isValidCompany("charlie")); // alphabets only
        assertTrue(Company.isValidCompany("Alpha")); // with capital letters
        assertTrue(Company.isValidCompany("SuperAlphaOmegaSquadCompany")); // long company names
    }

    @Test
    public void equals() {
        Company company = new Company("ValidCompany");

        // same values -> returns true
        assertTrue(company.equals(new Company("ValidCompany")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("OtherValidCompany")));
    }
}
