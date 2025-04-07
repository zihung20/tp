package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253").withNric("Txxxx123A")
            .withEmptyDuty()
            .withSalary("800").withCompany("Support").withRank("3SG")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432").withNric("Txxxx123B")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 3, 10))))
            .withSalary("1500").withCompany("HQ").withRank("CPT")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withAddress("wall street").withNric("Txxxx123C")
            .withEmptyDuty()
            .withSalary("1100").withCompany("Bravo").withRank("1SG")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withAddress("10th street").withNric("Txxxx123D")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 3, 11))))
            .withSalary("2000").withCompany("Alpha").withRank("LTA")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94821224")
            .withAddress("michegan ave").withNric("Txxxx123E")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 1, 5))))
            .withSalary("1300").withCompany("Charlie").withRank("COL")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94827427")
            .withAddress("little tokyo").withNric("Txxxx123F")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 4, 3))))
            .withSalary("1700").withCompany("Charlie").withRank("CWO")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94829442")
            .withAddress("4th street").withNric("Txxxx123G")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 2, 5))))
            .withSalary("600").withCompany("Bravo").withRank("REC")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84842424")
            .withAddress("little india").withNric("Txxxx123H")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 2, 1))))
            .withSalary("1300").withCompany("Charlie").withRank("3SG")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821313")
            .withAddress("chicago ave").withNric("Txxxx123I")
            .withDuty(new ArrayList<>(Arrays.asList(LocalDate.of(2025, 2, 2))))
            .withSalary("1400").withCompany("HQ").withRank("LTA")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_AMY)
            .withDuty(VALID_DUTY_AMY).withSalary(VALID_SALARY_AMY)
            .withCompany(VALID_COMPANY_AMY).withRank(VALID_RANK_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withNric(VALID_NRIC_BOB)
            .withDuty(VALID_DUTY_BOB).withSalary(VALID_SALARY_BOB)
            .withCompany(VALID_COMPANY_BOB).withRank(VALID_RANK_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
