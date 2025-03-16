package seedu.address.ui;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class DutyListPanel extends UiPart<Region> {

    private static final String FXML = "DutyListPanel.fxml";

    @FXML
    private ListView<LocalDate> dutyListView;

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
