import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class libraryManagementTest {

    private Library library;
    private Transaction transaction;
    private Book book;
    private Member member;

    @Before
    public void setUp() {
        // Initialize library, transaction, book, and member
        library = new Library();
        transaction = Transaction.getTransaction();
        try {
			book = new Book(101, "Test Book");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        member = new Member(1, "Test Member");
        library.addBook(book);
        library.addMember(member);
    }

    @Test
    public void testBorrowReturn() {
        // Assert the book is available before borrowing
        assertTrue("Book should be available", book.isAvailable());

        // Borrow the book
        boolean borrowResult = transaction.borrowBook(book, member);
        assertTrue("Book borrowing should be successful", borrowResult);
        assertFalse("Book should not be available after borrowing", book.isAvailable());

        // Try borrowing the same book again (should fail)
        boolean secondBorrowResult = transaction.borrowBook(book, member);
        assertFalse("Book borrowing should fail since it's already borrowed", secondBorrowResult);

        // Return the book
        transaction.returnBook(book, member);
        assertTrue("Book should be available after returning", book.isAvailable());

        // Try returning the same book again (should fail)
        transaction.returnBook(book, member);
        assertFalse("Book return should fail since it's already returned", book.isAvailable());
    }
}
