package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Duty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Salary;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a space-separated {@code oneBasedIndexes} into a list of {@code Index} objects and returns it.
     * Leading and trailing whitespaces will be trimmed. Each individual index in the string will be validated and
     * parsed using the {@link #parseIndex(String)} method.
     *
     * @param oneBasedIndexes A string containing one or more space-separated one-based indexes to be parsed.
     * @return A list of {@code Index} objects corresponding to the parsed one-based indexes.
     * @throws ParseException if any of the specified indexes are invalid (not non-zero unsigned integers).
     */
    public static List<Index> parseIndexList(String oneBasedIndexes) throws ParseException {
        List<String> indexArray = List.of(oneBasedIndexes.trim().split("\\s+"));
        List<Index> indexList = new ArrayList<>();
        for (String index : indexArray) {
            indexList.add(parseIndex(index));
        }
        return List.copyOf(indexList);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String salary} into a {@code Salary}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(salary)) {
            throw new ParseException(Salary.MESSAGE_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
    }

    /**
     * Parses a {@code String company} into a {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(company)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code String rank} into a {@code Rank}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rank} is invalid.
     */
    public static Rank parseRank(String rank) throws ParseException {
        requireNonNull(rank);
        String trimmedRank = rank.trim();
        if (!Rank.isValidRank(rank)) {
            throw new ParseException(Rank.MESSAGE_CONSTRAINTS);
        }
        return new Rank(trimmedRank);
    }

    //helper method generate by claude
    /**
     * Parses a date string and validates it exists.
     * Leading and trailing whitespaces will be trimmed in {@code String duty}
     *
     * @param duty The date string to parse
     * @return The trimmed duty string
     * @throws ParseException if the given {@code duty} has invalid format, or not exist date
     */
    public static String parseDuty(String duty) throws ParseException {
        requireNonNull(duty);
        String trimmedDuty = duty.trim();
        if (!Duty.DATE_REGEX_PATTERN.matcher(trimmedDuty).matches()) {
            throw new ParseException(String.format(Duty.MESSAGE_CONSTRAINTS));
        }
        try {
            String[] components = trimmedDuty.split("-");
            int year = Integer.parseInt(components[0]);
            int month = Integer.parseInt(components[1]);
            int day = Integer.parseInt(components[2]);

            if (month < 1 || month > 12) {
                throw new ParseException(String.format(Duty.MESSAGE_DATE_NOT_EXISTS, trimmedDuty));
            }

            int maxDays = getMaxDaysInMonth(year, month);
            if (day < 1 || day > maxDays) {
                throw new ParseException(String.format(Duty.MESSAGE_DATE_NOT_EXISTS, trimmedDuty));
            }
            return trimmedDuty;
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(Duty.MESSAGE_DATE_NOT_EXISTS, trimmedDuty));
        }
    }

    /**
     * Helper method to get the maximum number of days in a month
     *
     * @param year The year
     * @param month The month (1-12)
     * @return The maximum days in the month
     */
    private static int getMaxDaysInMonth(int year, int month) {
        return switch (month) {
        case 4, 6, 9, 11 -> 30;
        case 2 ->
                isLeapYear(year) ? 29 : 28;
        default -> 31;
        };
    }

    /**
     * Determines if the given year is a leap year
     *
     * @param year The year to check
     * @return true if it's a leap year, false otherwise
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
