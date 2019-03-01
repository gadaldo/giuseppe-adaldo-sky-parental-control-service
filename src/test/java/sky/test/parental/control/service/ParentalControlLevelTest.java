package sky.test.parental.control.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static sky.test.parental.control.service.ParentalControlLevel.M_12;
import static sky.test.parental.control.service.ParentalControlLevel.M_15;
import static sky.test.parental.control.service.ParentalControlLevel.M_18;
import static sky.test.parental.control.service.ParentalControlLevel.PG;
import static sky.test.parental.control.service.ParentalControlLevel.U;
import static sky.test.parental.control.service.ParentalControlLevel.getValueOf;

public class ParentalControlLevelTest {

    @Test
    public void shouldReturnValidParentalControlLevel() {
        assertEquals(M_18, getValueOf("18"));
        assertEquals(M_15, getValueOf("15"));
        assertEquals(M_12, getValueOf("12"));
        assertEquals(U, getValueOf("U"));
        assertEquals(PG, getValueOf("PG"));
    }

    @Test
    public void testOrdinalsForParentalLevels() {
        assertEquals(4, getValueOf("18").ordinal());
        assertEquals(3, getValueOf("15").ordinal());
        assertEquals(2, getValueOf("12").ordinal());
        assertEquals(1, getValueOf("PG").ordinal());
        assertEquals(0, getValueOf("U").ordinal());
    }

    @Test
    public void shouldReturnValueString() {
        assertEquals("U", U.value());
        assertEquals("PG", PG.value());
        assertEquals("12", M_12.value());
        assertEquals("15", M_15.value());
        assertEquals("18", M_18.value());
    }

    @Test
    public void testValueAndToString() {
        assertEquals(U.toString(), U.value());
        assertEquals(PG.toString(), PG.value());
        assertNotEquals(M_12.toString(), M_12.value());
        assertEquals("M_12", M_12.toString());
        assertNotEquals(M_15.toString(), M_15.value());
        assertEquals("M_15", M_15.toString());
        assertNotEquals(M_18.toString(), M_18.value());
        assertEquals("M_18", M_18.toString());
    }

    @Test
    public void shouldReturnNullForNonMatchingString() {
        assertNull(getValueOf("M_20"));
    }
}
