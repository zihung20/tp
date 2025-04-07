package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Nric nric;
    private final Rank rank;
    private final Company company;

    // Data fields
    private final Address address;
    private final Duty duty;
    private final Salary salary;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Address address, Nric nric,
        Duty duty, Salary salary, Company company, Rank rank) {
        requireAllNonNull(name, phone, address, nric, duty, salary, company, rank);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nric = nric;
        this.duty = duty;
        this.salary = salary;
        this.company = company;
        this.rank = rank;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Nric getNric() {
        return nric;
    }

    public void assignDuty(String duty) {
        this.duty.assignDuty(duty);
    }

    public boolean unassignDuty(String duty) {
        return this.duty.unassignDuty(duty);
    }

    public Duty getDuty() {
        return duty;
    }

    public boolean containsDutyDate(String dutyDate) {
        return duty.containsDutyDate(dutyDate);
    }

    public Salary getSalary() {
        return salary;
    }

    public Company getCompany() {
        return company;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Returns true if both persons have the same name and masked nric.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getNric().equals(getNric());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address)
                && nric.equals(otherPerson.nric)
                && duty.equals(otherPerson.duty)
                && salary.equals(otherPerson.salary)
                && company.equals(otherPerson.company)
                && rank.equals(otherPerson.rank);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, nric, duty, salary, company, rank);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("nric", nric)
                .add("salary", salary)
                .add("company", company)
                .add("rank", rank)
                .toString();
    }

}
