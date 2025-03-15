package seedu.address.model.util;

import java.time.LocalDate;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Duty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Duty EMPTY_DUTY = new Duty();
    public static final Duty SAMPLE_DUTY = new Duty(List.of(LocalDate.parse("2025-03-01"),
            LocalDate.parse("2024-03-02"), LocalDate.parse("2023-03-03")));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Nric("Txxxx123A"),
                EMPTY_DUTY),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Nric("Txxxx234A"),
                SAMPLE_DUTY),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Nric("Txxxx345A"),
                EMPTY_DUTY),
            new Person(new Name("David Li"), new Phone("91031282"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Nric("Txxxx456A"),
                SAMPLE_DUTY),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Nric("Txxxx567A"),
                EMPTY_DUTY),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Nric("Txxxx678A"),
                SAMPLE_DUTY),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
