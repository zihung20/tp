package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void duplicateDate() {
        Duty duty = new Duty();

        duty.assignDuty("2025-11-26");
        assertEquals(1, duty.getDuty().size());

        duty.assignDuty("2025-11-27");
        assertEquals(2, duty.getDuty().size());

        //duplicate date
        duty.assignDuty("2025-11-26");
        assertEquals(2, duty.getDuty().size());
    }

    @Test
    public void testEquals() {
        Duty duty1 = new Duty();
        Duty duty2 = new Duty();
        Duty duty3 = new Duty();

        duty1.assignDuty("2025-11-26");
        duty1.assignDuty("2025-11-27");

        duty2.assignDuty("2025-11-26");
        duty2.assignDuty("2025-11-27");

        duty3.assignDuty("2025-11-26");
        duty3.assignDuty("2025-11-28");

        assertTrue(duty1.equals(duty1));

        assertTrue(duty1.equals(duty2));

        assertFalse(duty1.equals(duty3));

        assertFalse(duty1.equals(null));

        assertFalse(duty1.equals("String"));
    }
}
