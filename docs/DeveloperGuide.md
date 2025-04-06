---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# HRQuickAccess Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

HRQuickAccess is based on the AddressBook-Level3 application. Learn more about the project [here](https://se-education.org/addressbook-level3/).

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](https://ay2425s2-cs2103t-t15-1a.github.io/tp/SettingUp.html).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `DutyListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component
- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.
- depends on some classes in the `Logic` component, as it displays the `Duty` object residing in the `Model`.
- handles user-triggered events such as command submissions and button clicks from the GUI.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. Returns a `CommandResult` which also carries UI instructions (e.g. show help, exit, or display person detail).
5. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list changes.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- stores data in a local JSON file located at `/data/addressbook.json`.
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Reassign Command

**API**: [`ReassignCommand.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/logic/commands/ReassignCommand.java)

<puml src="diagrams/ReassignCommandSequenceDiagram.puml" />

The `Reassign` feature, allows the reassigning of personnel's duties.
1. Command input entered by the user is passed through the UI component.
2. The UI component calls the execute function in the class `ReassignCommand`.
3. `ReassignCommand` will then unassign the old duty and then assign the new duty to the target personnel.
4. `ReassignCommand` returns the command execution result back to UI.
5. Finally, the UI component shows the updated entry to the user.

### Edit Command

**API**: [`EditCommand.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/logic/commands/EditCommand.java)

<puml src="diagrams/EditCommandObjectDiagram.puml" />

The `Edit` feature allows users to edit existing personnel's entry information.

During the process of an edit command, a **personToEdit** `Person` object would be referenced to store the attributes of the target entry.
Additionally, an **updatedPerson** `Person` object would be created which represents the new `Person` object to replace **personToEdit**.

The **updatedPerson** objects will have the new updated attributes indicated by the user, while the rest of the attributes which are not edited would be storing **personToEdit** original attribute.

In the given UML diagram example, it can be seen that the **updatedPerson**'s `Duty` object is still the same as the `Duty` object from **personToEdit**, while updated attributes such as `Rank`, `Company`, `Address` and `Name` are different objects.

### Filter Command

**API**: [`FilterCommand.java`](https://github.com/AY2425S2-CS2103T-T15-1a/tp/blob/master/src/main/java/seedu/address/logic/commands/FilterCommand.java)

<puml src="diagrams/FilterCommand.puml" />

The `Filter` feature allows users to filter personnel's entry according to their `Company` attribute.

`FilterCommand` uses the class `CompanyContainsKeywordsPredicate` which in turn tests a given `Person` object to find out whether they fit the keyword given.

This way, the feature is able to filter out entries that are not from the intended `Company`.

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

S1 Branch staff (i.e. Human resource) for an Army Battalion, responsible for managing and organizing extensive troop information across various roles, ranks, and duties. The HR staff needs quick, efficient access to troop details for streamlined communication and duty scheduling.

**Value proposition**:

1. HRQuickAccess provides HR personnel with fast, keyboard-focused access to essential troop information, including contact details and duty dates

   **Rationale**: The Singapore Armed Forces (SAF) primarily issue and utilize laptops with security cards within army camps; as such, tech accessories such as mouses and additional monitors are less common and applicable. Through command line interface (CLI) interface, we can overcome this constraint

2. HRQuickAccess does not require internet access to operate, as all its data is stored locally instead of remotely

   **Rationale**: SAF-issued laptops cannot be connected to the internet (less INET laptops) for security reasons. By having a local database, we can work around this constraint

3. HRQuickAccess offers a better alternative to the current system of using Microsoft Excel, which is prone to human errors, by offering ease of use, quick lookups, and smooth updates, as well as a clean interface

   **Rationale**: There are currently no better alternatives for HR staff who prefer a fast, typing-focused workflow, leaving many to rely on Microsoft Excel. However, Excel is prone to human error, inefficient for quick lookups, and cumbersome for regular updates

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a/an …​              | I want to …​                                                 | So that I can…​                                                           |
| -------- |-------------------------|--------------------------------------------------------------| ------------------------------------------------------------------------- |
| `* * *`  | S1 Branch Staff         | add entries                                                  | add contacts into the address book                                        |
| `* * *`  | S1 Branch Staff         | delete entries                                               | remove contacts into the address book                                     |
| `* * *`  | S1 Branch Staff         | view entries                                                 | see a personnel details                                                   |
| `* * *`  | S1 Branch Staff         | assign duties                                                | assign more duties to a personnel                                         |
| `* *`    | S1 Branch Staff         | have a cleaner GUI (i.e concise, minimalistic)               | not get tired looking at the app all day.                                 |
| `* * *`  | S1 Branch Staff         | be able to add pictures to profile entries                   | recognize the entries easily.                                             |
| `* *`    | S1 Branch Staff         | have a quick search function using partial names or keywords | find personnels even if I don’t remember their full details               |
| `* *`    | S1 Branch Staff         | quickly access and update the duty dates of troops           | efficiently manage scheduling, deployments, and rotations without delays. |
| `* *`    | user from the S1 Branch | have a filter feature                                         | so that I can filter the companies of personnel that I want to know about.      |

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `HRQuickAccess` and the **Actor** is the `Branch Staff`, unless specified otherwise)

#### **Use Case: UC1 - Add a new personnel’s entry**

**Preconditions:**

- The system must be on the home page.
- The system must be able to connect to its local database without issues.

**Main Success Scenario (MSS):**

1. The branch staff types a command to add a new personnel entry, including all required details.

2. The system adds the personnel’s entry with the provided details.

3. The system displays a success message to the branch staff.
    Use case ends.

**Extensions:**

- 1a. The branch staff provides insufficient input.

  - 1a1. The system displays an error message indicating the required fields and correct format to enter.

    Use case resumes at step 1.

- 1b. The system detects invalid information entered.

  - 1b1. The system states one invalid field provides the correct format to enter.

    Use case resumes at step 1.

- 1c. The system detects a duplicate personnel.

  - 1c1. The system displays an error message stating that the entry already exists.

    Use case resumes at step 1.

#### **Use Case: UC2 - View a personnel’s entry**

**Preconditions:**

- The system must be on the home page.
- The system must be able to connect to its local database without issues.

**Main Success Scenario (MSS):**

1.  The branch staff request to view a personnel’s entry.
2.  The branch staff enters the details of the personnel.
3.  The system shows the personnel’s entry.

    Use case ends.

**Extensions:**

- 2a. The branch staff provides insufficient input.

  - 2a1. The system displays an error message indicating the required fields and correct format to enter.

    Use case resumes at step 2.

- 2b. The system detects invalid information in the provided details.

  - 2b1. The system states the incorrect fields and provides the correct format to enter.

    Use case resumes at step 2.

- 2c. The system check that there isn’t such personnel.

  - 2c1. The system displays an error message stating that the personnel cannot be found.

    Use case end.

- 2d. The system found that there are multiple personnel's entries.

  - 2d1. The system displays a list of matching personnel and asks which entry to view.
  - 2d2. The branch staff selects the specific entry to view.

    Use case resumes at step 3.

#### **Use Case: UC3 - Delete a personnel’s entry**

**Preconditions:**

- The system must be on the home page.
- The system must be able to connect to its local database without issues.

**Main Success Scenario (MSS):**

1.  The branch staff request to delete a personnel’s entry.
2.  The branch staff enters the details of the personnel.
3.  The system requests confirmation for deletion.
4.  The branch staff select confirm.
5.  The system deletes the entry from the database.
6.  The system displays a success message to the branch staff.

    Use case ends.

**Extensions:**

- 2a. The branch staff provides insufficient input.

  - 2a1. The system displays an error message indicating the required fields and correct format to enter.

    Use case resumes at step 2.

- 2b. The system detects invalid information in the provided details.

  - 2b1. The system states the incorrect fields and provides the correct format to enter.

    Use case resumes at step 2.

- 2c. The system check that there isn’t such personnel.

  - 2c1. The system displays an error message stating that the personnel cannot be found.

    Use case end.

- 2d. The system found that there are multiple personnels’ entries.

  - 2d1. The system displays a list of matching personnel and asks which entry to delete.
  - 2d2. The branch staff selects the specific entry to delete.

    Use case resumes at step 3.

- 4a. The branch staff decides to not delete the personnel’s entry on second thought.

  - 4a1. The branch staff denies the confirmation.

    Use case end.

#### **Use Case: UC4 - Assign a duty to a personnel’s entry**

**Preconditions:**

- The system must be on the home page.
- The system must be able to connect to its local database without issues.

**Main Success Scenario (MSS):**

1.  The branch staff request to add duty to a personnel’s entry.
2.  The branch staff enters the duty’s details of the personnel.
3.  The system assigns the duty to the personnel’s entry.
4.  The system displays a success message to the branch staff and shows related changes.

    Use case ends.

**Extensions:**

- 2a. The branch staff provides insufficient input.

  - 2a1. The system displays an error message indicating the required fields and correct format to enter.

    Use case resumes at step 2.

- 2b. The system detects invalid information in the provided details.

  - 2b1. The system states the incorrect fields and provides the correct format to enter.

    Use case resumes at step 2.

- 2c. The system check that there isn’t such personnel.

  - 2c1. The system displays an error message stating that the personnel cannot be found and thus cannot be assigned a duty.

    Use case end.

- 2d. The system found that there are multiple personnels’ entries.

  - 2d1. The system displays a list of matching personnel and asks which entry to assign a duty to.
  - 2d2. The branch staff selects the specific entry to assign a duty to.

    Use case resumes at step 3.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to handle a battalion-level number of people’s (500–800) entries without noticeable lag.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Error messages should avoid jargons that non-technical users may not understand, while omitting explanations of military jargon, as they are already familiar with them.
5.  Entries’ data should be stored locally.
6.  Given n is number of all data entries, search algorithm for looking up entries should be O(n) bounded.
7.  The application should remain usable without internet access at all times.

_{More to be added}_

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS
- **Battalion**: A large military unit consisting of multiple companies, typically includes 500 to 800 soldiers. It is usually commanded by a battalion commander and forms a key component within a brigade or regiment.
- **Company**: A military unit made up of several platoons, usually commanded by a captain. In Singapore Armed Forces, a company generally comprises around 100 to 150 soldiers.
- **Platoon**: A smaller military unit consisting of a few squads, typically led by a lieutenant. Platoons usually include 20 to 50 soldiers and serve as the basic building blocks of a company.
- **Rank**: The hierarchical level or position of a soldier or officer within the military. Ranks indicate authority, responsibilities, and career progression.
- **Duties**: The scheduled assignments or tasks for soldiers during a particular month, including specific dates and the maximum number of working hours allowed per month.
- **Masked NRIC**: A version of a soldier's National Registration Identity Card (NRIC) number where certain digits or characters are redacted to comply with PDPR regulations and protect sensitive personal information.
- **Branch Staff**: Personnel responsible for managing human resources within a battalion or across the army. They handle administrative tasks, maintain personnel entries, and coordinate manpower for military operations.
- **Personnel**: Refers to all individuals serving in the military, including recruits, soldiers, and other service members in Singapore who are either fulfilling National Service requirements or serving voluntarily.
- **National Service (NS)**: A mandatory service program for eligible Singaporean citizens and permanent residents, involving military training and service in the Singapore Armed Forces.
- **Duty Date**: A specific calendar day where a soldier is scheduled to perform assigned tasks or responsibilities. Each soldier can only perform duty on a limited number of days within the same month, as regulated by SAF policies.
- **Inet Laptops**: Official laptops provided by SAF for secure internet access in designated areas

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

   1. `preferences.json`
      1. Move the `preferences.json` file to a different location.
      2. Relaunch the app.<br>
         Expected: A new `preferences.json` file is created with default preferences.
   2. `config.json`
      1. Move the `config.json` file to a different location.
      2. Relaunch the app.<br>
         Expected: A new `config.json` file is created with default preferences.
   3. `addressbook.json`
      1. Missing `addressbook.json`
         1. Move the `addressbook.json` file to a different location.
         2. Relaunch the app.<br>
          Expected: Application loads with sample data.
      2. Corrupted `addressbook.json`
         1. Corrupt the `addressbook.json` file by adding some random text.
         2. Relaunch the app.<br>
          Expected: Applications loads without any data.
