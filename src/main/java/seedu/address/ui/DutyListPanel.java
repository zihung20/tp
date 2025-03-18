package seedu.address.ui;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of duty dates.
 */
public class DutyListPanel extends UiPart<Region> {

    private static final String FXML = "DutyListPanel.fxml";

    @FXML
    private ListView<LocalDate> dutyListView;

    /**
     * Creates a {@code DutyListPanel} with the given {@code ObservableList}.
     */
    public DutyListPanel(ObservableList<LocalDate> dutyList) {
        super(FXML);
        dutyListView.setItems(dutyList);
    }

    /**
     * Updates the duty list view to show the duty list of the given person.
     */
    public void updateDutyList(ObservableList<LocalDate> dutyList) {
        dutyListView.setItems(dutyList);
    }
}
