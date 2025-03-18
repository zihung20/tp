package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        assertEquals(1, duty.getDutyList().size());

        duty.assignDuty("2025-11-27");
        assertEquals(2, duty.getDutyList().size());

        //duplicate date
        duty.assignDuty("2025-11-26");
        assertEquals(2, duty.getDutyList().size());
    }

    @Test
    public void dutiesNumberOfCurrentMonth() {
        Duty duty = new Duty();

        LocalDate current = LocalDate.now();
        LocalDate previous = current.minusMonths(1);
        LocalDate next = current.plusMonths(1);

        duty.assignDuty(current.format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN)));
        duty.assignDuty(previous.format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN)));
        duty.assignDuty(next.format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN)));
        assertEquals(1, duty.getDutyCount());

        current = current.plusYears(20);
        duty.assignDuty(current.format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN)));
        assertEquals(1, duty.getDutyCount());
    }

    @Test
    public void constructorWithValidDutyList() {
        List<LocalDate> dutyList = List.of(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 2, 2),
                LocalDate.of(2025, 3, 3)
        );
        Duty duty = new Duty(dutyList);
        assertEquals(dutyList, duty.getDutyList());
    }

    @Test
    public void constructorWithEmptyDutyList() {
        Duty duty = new Duty();
        assertEquals(0, duty.getDutyList().size());
    }

    @Test void testToString() {
        Duty duty1 = new Duty();
        Duty duty2 = new Duty();
        Duty duty3 = new Duty();
        String date1 = LocalDate.now().format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN));
        String date2 =
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern(Duty.DATE_PATTERN));

        duty1.assignDuty(date1);
        duty1.assignDuty(date2);

        duty2.assignDuty(date1);
        duty2.assignDuty(date2);

        duty3.assignDuty(date1);

        assertTrue(duty1.toString().equals(duty2.toString()));
        assertFalse(duty1.toString().equals(duty3.toString()));
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
