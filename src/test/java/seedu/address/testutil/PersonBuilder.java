package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Duty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Salary;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NRIC = "Txxxx567A";
    public static final List<LocalDate> DEFAULT_DUTY =
        new ArrayList<>(Arrays.asList(LocalDate.now()));
    public static final String DEFAULT_SALARY = "1000";
    public static final String DEFAULT_COMPANY = "Alpha";
    public static final String DEFAULT_RANK = "3SG";

    private Name name;
    private Phone phone;
    private Address address;
    private Nric nric;
    private Duty duty;
    private Salary salary;
    private Company company;
    private Rank rank;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        nric = new Nric(DEFAULT_NRIC);
        duty = new Duty();
        salary = new Salary(DEFAULT_SALARY);
        company = new Company(DEFAULT_COMPANY);
        rank = new Rank(DEFAULT_RANK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        address = personToCopy.getAddress();
        nric = personToCopy.getNric();
        duty = personToCopy.getDuty();
        salary = personToCopy.getSalary();
        company = personToCopy.getCompany();
        rank = personToCopy.getRank();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Duty} of the {@code Person} that we are building,
     * with an empty duty list.
     */
    public PersonBuilder withEmptyDuty() {
        this.duty = new Duty();
        return this;
    }

    /**
     * Sets the {@code Duty} of the {@code Person} that we are building.
     */
    public PersonBuilder withDuty(List<LocalDate> duty) {
        this.duty = new Duty(duty);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Person} that we are building.
     */
    public PersonBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Rank} of the {@code Person} that we are building.
     */
    public PersonBuilder withRank(String rank) {
        this.rank = new Rank(rank);
        return this;
    }

    public Person build() {
        return new Person(name, phone, address, nric, duty, salary, company, rank);
    }

}
