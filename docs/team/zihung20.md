---
  layout: default.md
  title: "ZiHung Portfolio Page"
---

# Project Portfolio Page for Zi Hung

## Overview
HRQuickAccess is a desktop application designed for S1 Branch HR staff to manage trooper information efficiently. It provides fast, keyboard-focused access to personnel records, including contact details and duty dates, while ensuring offline functionality in compliance with SAF security constraints. The application is optimized for use via a Command Line Interface (CLI) while retaining the benefits of a Graphical User Interface (GUI). [see more at here](https://ay2425s2-cs2103t-t15-1a.github.io/tp/)

## Summary of Contributions

### Code Contributed
- View my code contributions on the [tP Code Dashboard](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/?search=zihung20&breakdown=true).

### Enhancements Implemented

- **Duty Class and Validation**: I implemented the `Duty` class to represent trooper duty dates, along with comprehensive test code to ensure its behavior met product requirements. This enhancement allows HR staff to reliably track and validate duty schedules (e.g., ensuring a duty date is a real, future date). The implementation was challenging due to the need for robust date parsing and validation logic to handle various edge cases, such as invalid formats or past dates. I wrote unit tests to verify functionality, including positive cases (e.g., "2025-04-15") and negative cases (e.g., "2020-02-30"), which required integrating the class with the existing data model and ensuring compliance with SAF offline constraints.

- **Assign and Unassign Commands**: I designed and implemented the `assign` and `unassign` commands to manage trooper duty assignments via the CLI. These commands provide the foundational structure for assigning personnel to duties and removing them, forming the basis for the later `reassign` command (a composite of `unassign` followed by `assign`). The feature is complete as it handles core use cases, such as assigning a trooper to a duty and gracefully rejecting invalid inputs (e.g., non-existent index). The difficulty arose from ensuring command consistency within the CLI framework and maintaining data integrity in an offline environment. Although the commands were later tweaked by the team, my initial setup laid the groundwork for subsequent refinements.

- **Personnel Profile with Image Matching**: I developed the personnel profile feature, enabling HR staff to add and manage trooper profiles efficiently. A key enhancement was linking each profile to a photo in the local directory based on the trooper’s identity (name and NRIC). This allows for quick visual identification within the GUI while adhering to offline security requirements. The implementation was complex due to the need to design a file-matching system that dynamically retrieves images using a naming convention (e.g., `Name_NRIC.jpg`) and handles cases like missing or mismatched files. I ensured completeness by adding error handling for invalid inputs and providing feedback via the CLI, making this a user-friendly yet technically demanding addition.

### Contributions to the [User Guide (UG)](../UserGuide.md)
- **Validated UG Format and Product Specification Alignment**: I reviewed and refined sections of the UG to ensure consistent formatting (e.g., standardized command syntax presentation) and compliance with product specifications. For instance, I verified that instructions for adding personnel profiles (e.g., including photo linking via NRIC) were clear and reflected the offline functionality required by SAF security constraints. This involved cross-checking the UG against the actual application behavior to guarantee accuracy and usability for HR staff.

### Contributions to the [Developer Guide (DG)](../DeveloperGuide.md)

- **Added Object Diagram for Edit Command**: I contributed an object diagram to illustrate the behavior of the `edit` command, specifically showing that a new `Duty` object is not created during an update. This diagram clarifies how the object point to an existing `Duty` instance in the personnel record (e.g., updating a duty date) rather than copy a new one, preserving data integrity.

- **Refined Use Cases**: I revised the use case section to provide developers with a clear understanding of HRQuickAccess’s intended functionality.

- **Updated Non-Functional Requirements (NFR) and Glossary**: I enhanced the NFR section to reflect the product’s constraints, such as offline functionality and SAF security compliance (e.g., "The system must operate without internet access and encrypt all local data"). I also refined the glossary to include precise definitions for terms like "Duty", "INET" and "Battalion," ensuring consistency with the application’s design and implementation. 

### Contributions to Team-Based Tasks

- **Participated in Feature Prioritization Brainstorming**: I actively contributed to team brainstorming sessions to prioritize features for HRQuickAccess, ensuring we focused on critical functionalities.

- **Set Up Repository and Workflow**: I initialized the project repository, creating essential milestones (e.g., "v1.2", "v1.3"...) and labels (e.g., "bug," "enhancement," "alpha-bug") to organize our development process. I also configured CI/CD settings to automate testing and deployment checks, streamlining the team’s workflow and ensuring code quality from the start.

- **Labeled Issues for Clarity**: I tagged and categorized issues in the repository (e.g., "Duty Validation Bug," "Assign Command Enhancement") to make them easily identifiable and assignable. This helped the team address fixes and features independently, improving efficiency and reducing overlap in effort.

### Review/Mentoring Contributions

- **Reviewed Pull Requests**: I reviewed several pull requests to ensure code quality and consistency, such as [PR #53](https://github.com/AY2425S2-CS2103T-T15-1a/tp/pull/53) where I catch the code quality issue, and [PR #77](https://github.com/AY2425S2-CS2103T-T15-1a/tp/pull/77) where I find the diagram was not align with the correct format.

### Acknowledgements: Use of AI Tools
Throughout the development of HRQuickAccess, I leveraged AI tools such as [ChatGPT](https://chat.openai.com) and [Grok](https://x.ai) to assist with specific tasks. These tools mainly helped me fix commit messages, correct grammar errors, refine code, and check code quality.
