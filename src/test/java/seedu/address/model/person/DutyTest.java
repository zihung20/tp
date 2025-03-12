package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DutyTest {

    @Test
    public void assignInvalidDate() {

        //Invalid format
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("2025/11/26"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("2025.11.26"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("26-11-2025"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("11-26-2025"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("2025 11 26"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("26 Nov 2025"));
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("November 26, 2025"));
        //Invalid date
        assertThrows(IllegalArgumentException.class, () -> new Duty().assignDuty("2025-13-40"));
    }

    @Test
    public void assignValidDate() {
        assertDoesNotThrow(() -> new Duty().assignDuty("2025-11-26"));
    }

    @Test
    public void testEquals() {
        Duty duty1 = new Duty();
        Duty duty2 = new Duty();
        Duty duty3 = new Duty();

        // Adding valid dates using assignDuty()
        duty1.assignDuty("2025-11-26");
        duty1.assignDuty("2025-11-27");

        duty2.assignDuty("2025-11-26");
        duty2.assignDuty("2025-11-27");

        duty3.assignDuty("2025-11-26");
        duty3.assignDuty("2025-11-28");

        // Same duties, should be equal
        assertEquals(duty1, duty2);

        // Different duties, should not be equal
        assertNotEquals(duty1, duty3);

        // Comparing with null, should not be equal
        assertNotEquals(null, duty1);

        // Comparing with a different type, should not be equal
        assertNotEquals("String", duty1);

    }

}
