package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private DutyListPanel dutyListPanel;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, DutyListPanel dutyListPanel) {
        super(FXML);
        this.dutyListPanel = dutyListPanel;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Clears the selection in the person list.
     */
    public void clearSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Highlights the selected person in person list.
     */
    public void highlightSelectedPerson(Person person) {
        int index = personListView.getItems().indexOf(person);
        if (index != -1) {
            personListView.scrollTo(index);
            personListView.getSelectionModel().select(index);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                PersonCard personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
                setOnMouseClicked(event -> handlePersonCardClick(personCard));
            }
        }

        private void handlePersonCardClick(PersonCard personCard) {
            Person clickedPerson = personCard.person;
            logger.info("Clicked on " + clickedPerson.getName().fullName);
            ObservableList<LocalDate> dutyList = FXCollections.observableList(clickedPerson.getDuty().getDutyList());
            dutyListPanel.updateDutyList(dutyList);
        }
    }

}
