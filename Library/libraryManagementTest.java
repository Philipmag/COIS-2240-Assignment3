import org.junit.Test;
import static org.junit.Assert.*;

public class libraryManagementTest {

    @Test
    public void testBookId() throws Exception {
        // Test valid boundary cases
        Book validBookLow = new Book(100, "Valid Book Low");
        assertEquals(100, validBookLow.getId());
        assertEquals("Valid Book Low", validBookLow.getTitle());

        Book validBookHigh = new Book(999, "Valid Book High");
        assertEquals(999, validBookHigh.getId());
        assertEquals("Valid Book High", validBookHigh.getTitle());

        // Test invalid cases
        try {
            new Book(99, "Invalid Book Low");
            fail("Expected an exception for ID less than 100.");
        } catch (Exception e) {
            assertEquals("Invalid book ID. Must be between 100 and 999.", e.getMessage());
        }

        try {
            new Book(1000, "Invalid Book High");
            fail("Expected an exception for ID greater than 999.");
        } catch (Exception e) {
            assertEquals("Invalid book ID. Must be between 100 and 999.", e.getMessage());
        }
    }
}
