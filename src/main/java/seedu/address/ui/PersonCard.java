package seedu.address.ui;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "PersonListCard.fxml";
    // Common accepted format
    private static final String[] AVAILABLE_IMAGE_FORMATS = new String[]{"jpg", "jpeg", "png"};
    public final Person person;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


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
    @FXML
    private ImageView personnelImage;


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
        setImage(person.getNric().maskNric);

        if (dutyCount < 6) {
            duty.setStyle("-fx-text-fill: #32CD32;");
        } else if (dutyCount <= 15) {
            duty.setStyle("-fx-text-fill: yellow;");
        } else {
            duty.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Set the image of the person using the fileName, accepted formats are declared
     * in {@link #AVAILABLE_IMAGE_FORMATS}. If the file cannot be found,
     * no action is taken and the component will keep its current image.
     *
     * @param fileName the file name of the image, e.g., "Txxxx123A"
     */
    private void setImage(String fileName) {
        for (String format : AVAILABLE_IMAGE_FORMATS) {
            try {
                Path imagePath = Paths.get("data", "images", fileName + "." + format);
                if (Files.exists(imagePath)) {
                    Image image = new Image(imagePath.toUri().toString());
                    personnelImage.setImage(image);
                    return;
                }
            } catch (InvalidPathException e) {
                logger.severe("An error occurred while getting the file path for personnel Image: " + e);
            }
        }
    }
}
