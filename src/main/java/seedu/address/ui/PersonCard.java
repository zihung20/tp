package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label nric;
    @FXML
    private Label duty;
    @FXML
    private Label salary;
    @FXML
    private Label company;
    @FXML
    private Label rank;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        nric.setText(person.getNric().maskNric);
        salary.setText("$" + person.getSalary().toString());
        company.setText(person.getCompany().fullCompany);
        rank.setText(person.getRank().fullRank + " ");

        int dutyCount = person.getDuty().getDutyCount();
        duty.setText(String.valueOf(dutyCount));

        if (dutyCount < 6) {
            duty.setStyle("-fx-text-fill: #32CD32;");
        } else if (dutyCount <= 15) {
            duty.setStyle("-fx-text-fill: yellow;");
        } else {
            duty.setStyle("-fx-text-fill: red;");
        }
    }
}
