package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a person's duties history.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Duty {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should not be blank and must follow ISO 8601 format: yyyy-MM-dd";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final Pattern DATE_REGEX_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    public static final String MESSAGE_DATE_NOT_EXISTS = "The input date %s does not exist.";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private final List<LocalDate> duty;

    /**
     * Constructs a Duty object that has no duty dates at first.
     */
    public Duty() {
        duty = new ArrayList<>();
    }

    /**
     * Constructs a Duty object with a list of duty dates.
     */
    public Duty(List<LocalDate> duty) {
        this.duty = duty;
    }

    /**
     * Checks if a given date string is valid according to the specified pattern.
     *
     * @param dateString The date string to validate.
     * @return True if the date string is valid, false otherwise.
     */

    public static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Assigns a duty date for this person, do nothing if the date already existed
     *
     * @param dateString The duty date to assign for this person.
     * @throws IllegalArgumentException If the input is not in the correct format.
     */
    public void assignDuty(String dateString) {
        if (!isValidDate(dateString)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        LocalDate date = LocalDate.parse(dateString, FORMATTER);
        if (!duty.contains(date)) {
            duty.add(date);
        }
    }

    /**
     * Removes a duty from the duty list using its string representation, returning true if successfully removed.
     *
     * @param dateString the dateString to be removed
     * @return true if successfully removed, false otherwise
     * @throws IllegalArgumentException if the dateString is not in the correct format
     */
    public boolean unassignDuty(String dateString) {
        if (!isValidDate(dateString)) {
            //should not reach here as parse handle
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        LocalDate date = LocalDate.parse(dateString, FORMATTER);
        return duty.remove(date);
    }

    /**
     * Returns a boolean which indicates if the given date is in the duty list.
     *
     * @param dateString the dateString to be checked.
     * @return true if duty list contains said date.
     */
    public boolean containsDutyDate(String dateString) {
        if (!isValidDate(dateString)) {
            //should not reach here as parse handle
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        LocalDate date = LocalDate.parse(dateString, FORMATTER);
        return duty.contains(date);
    }

    public List<LocalDate> getDutyList() {
        return duty;
    }

    /**
     * Gets the duty count for the current month.
     *
     * @return The duty count of the personnel for the current month.
     */
    public int getDutyCount() {
        return (int) duty.stream()
                .filter(date -> date.getYear() == LocalDate.now().getYear())
                .filter(date -> date.getMonth() == LocalDate.now().getMonth())
                .count();
    }

    @Override
    public String toString() {
        return duty.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Duty obj)) {
            return false;
        }

        return Objects.equals(duty, obj.duty);
    }

    @Override
    public int hashCode() {
        return duty.hashCode();
    }
}
