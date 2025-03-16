package seedu.address.storage;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Duty;

/**
 * Jackson-friendly version of {@link seedu.address.model.person.Duty}.
 */
public class JsonAdaptedDuty {

    private final List<String> dutyList;

    /**
     * Constructs a {@code JsonAdaptedDuty} with the given duty {@code duty}
     */
    @JsonCreator
    public JsonAdaptedDuty(List<String> duty) {
        this.dutyList = duty;
    }

    /**
     * Converts a given {@code Duty} into this class for Jackson use.
     */
    public JsonAdaptedDuty(Duty source) {
        dutyList = source.getDutyList().stream()
                .map(date -> date.format(Duty.FORMATTER))
                .collect(Collectors.toList());
    }

    @JsonValue
    public List<String> getDutyList() {
        return dutyList;
    }

    /**
     * Converts this Jackson-friendly adapted duty object into the model's {@code Duty} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted duty.
     */
    public Duty toModelType() throws IllegalValueException {
        Duty duty = new Duty();
        for (String dateString : dutyList) {
            duty.assignDuty(dateString);
        }
        return duty;
    }
}
