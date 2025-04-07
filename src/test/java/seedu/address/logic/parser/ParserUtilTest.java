package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "Rachel@@W@lker";
    private static final String INVALID_NRIC = "Axxxx123A";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";

    private static final String VALID_NAME = "Rachel W@lker";
    private static final String VALID_NRIC = "Sxxxx567A";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseIndexList_invalidInput_throwsParseException() {
        // Parse 1 valid index and 1 invalid index
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexList("10 a"));

        // Parse 1 invalid index
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexList("a"));

        // Parse 2 valid with commas
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexList("1, 2"));
    }

    @Test
    public void parseIndexList_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndexList(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndexList_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                ParserUtil.parseIndexList("1 2"));

        // Leading and trailing whitespaces
        assertEquals(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                ParserUtil.parseIndexList("  1  2    "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseNric_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNric((String) null));
    }

    @Test
    public void parseNric_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNric(INVALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithoutWhitespace_returnsNric() throws Exception {
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(VALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithWhitespace_returnsTrimmedNric() throws Exception {
        String nricWithWhitespace = WHITESPACE + VALID_NRIC + WHITESPACE;
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(nricWithWhitespace));
    }

    @Test
    public void parseDuty_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDuty(null));
    }

    @Test
    public void parseDuty_invalidFormat_throwsParseException() {
        //Invalid format, should be yyyy-mm-dd
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023/01/01"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("01-01-2023"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-1-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("20230101"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("abcd-ef-gh"));
    }

    @Test
    public void parseDuty_invalidDateValues_throwsParseException() {
        // Invalid month values
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-00-01"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-13-01"));

        // Invalid day values
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-01-00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-01-32"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-04-31")); // April has 30 days

        // Invalid February dates
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-02-29")); // Non-leap year
        assertThrows(ParseException.class, () -> ParserUtil.parseDuty("2023-02-30"));
    }

    @Test
    public void parseDuty_validLeapYearDate_success() throws Exception {
        // 2024 is a leap year, so February 29 is valid
        String validLeapDate = "2024-02-29";
        assertEquals(validLeapDate, ParserUtil.parseDuty(validLeapDate));
    }

    @Test
    public void parseDuty_validValueWithoutWhitespace_returnsDuty() throws Exception {
        String validDate = "2023-01-15";
        assertEquals(validDate, ParserUtil.parseDuty(validDate));

        // Test for month with 30 days
        validDate = "2023-04-30";
        assertEquals(validDate, ParserUtil.parseDuty(validDate));

        // Test for month with 31 days
        validDate = "2023-05-31";
        assertEquals(validDate, ParserUtil.parseDuty(validDate));
    }

    @Test
    public void parseDuty_validValueWithWhitespace_returnsTrimmedDuty() throws Exception {
        String dutyWithWhitespace = WHITESPACE + "2023-01-15" + WHITESPACE;
        String expectedDuty = "2023-01-15";
        assertEquals(expectedDuty, ParserUtil.parseDuty(dutyWithWhitespace));
    }
}
