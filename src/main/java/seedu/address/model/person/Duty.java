package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a person's duties history.
 */
public class Duty {
    public static final String MESSAGE_CONSTRAINTS = "Date should not be blank and must follow ISO 8601 format: yyyy-MM-dd";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private final List<LocalDate> duties;

    /**
     * Constructs a Duty object that has no duty dates at first.
     */
    public Duty() {
        duties = new ArrayList<>();
    }

    /**
     * Checks if a given date string is valid according to the specified pattern.
     *
     * @param dateString The date string to validate.
     * @return True if the date string is valid, false otherwise.
     */
    public boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, FORMATTER);
            return true;  // Valid date
        } catch (DateTimeParseException e) {
            return false; // Invalid date
        }
    }

    /**
     * Assigns a duty date for this person.
     *
     * @param dateString The duty date to assign for this person.
     * @throws IllegalArgumentException If the input is not in the correct format.
     */
    public void assignDuty(String dateString) {
        if (!isValidDate(dateString)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        duties.add(LocalDate.parse(dateString, FORMATTER));
    }

    @Override
    public String toString() {
        return duties.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Duty obj)) {
            return false;
        }

        return Objects.equals(duties, obj.duties);
    }

    @Override
    public int hashCode() {
        return duties.hashCode();
    }
}
