import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class LibraryManagementTest {

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

    @Test
    public void testBorrowReturn() throws Exception {
        // Instantiate a Book and a Member
        Book book = new Book(101, "Book for Borrowing");
        Member member = new Member("John Doe", "john.doe@example.com");

        // Instantiate a Transaction instance
        Transaction transaction = Transaction.getTransaction();

        // Assert that the book is available
        assertTrue("Book should be available", book.isAvailable());

        // Borrow the book
        boolean borrowSuccess = transaction.borrowBook(book, member);
        assertTrue("Borrowing the book should be successful", borrowSuccess);
        assertFalse("Book should be unavailable after borrowing", book.isAvailable());

        // Try borrowing the same book again (should fail)
        borrowSuccess = transaction.borrowBook(book, member);
        assertFalse("Borrowing the book again should fail", borrowSuccess);

        // Return the book
        boolean returnSuccess = transaction.returnBook(book, member);
        assertTrue("Returning the book should be successful", returnSuccess);
        assertTrue("Book should be available after returning", book.isAvailable());

        // Try returning the book again (should fail)
        returnSuccess = transaction.returnBook(book, member);
        assertFalse("Returning the book again should fail", returnSuccess);
    }

    @Test
    public void testSingletonTransaction() throws Exception {
        // Step 1: Get the constructor of the Transaction class
        Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();

        // Step 2: Get the modifiers of the constructor
        int modifiers = constructor.getModifiers();

        // Step 3: Assert that the constructor is private (modifier value should be 'private' or 2)
        assertTrue("Constructor should be private", Modifier.isPrivate(modifiers));

        // Step 4: Try to instantiate using reflection (this should throw an exception)
        try {
            constructor.setAccessible(true);  // Make constructor accessible
            Transaction transaction1 = constructor.newInstance();  // Try instantiating
            fail("Should have thrown an exception when attempting direct instantiation.");
        } catch (Exception e) {
            // Expected exception, we pass the test if this block is hit
            // Assert that the exception is either IllegalAccessException or InstantiationException
            assertTrue(e instanceof IllegalAccessException || e instanceof InstantiationException);
        }

        // Step 5: Ensure the singleton pattern by checking the instance is the same
        Transaction transaction2 = Transaction.getTransaction();
        assertNotNull("Transaction instance should not be null", transaction2);

        // Step 6: Assert that the instance returned by getTransaction is the same as the previous instance
        Transaction transaction3 = Transaction.getTransaction();
        assertSame("Transaction instances should be the same", transaction2, transaction3);
    }
}
