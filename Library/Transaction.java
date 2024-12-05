import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;


public class Transaction {
    private static Transaction instance;

    // Private constructor to prevent instantiation
    private Transaction() {
        // Initialization logic
    }

    // Singleton method to return the single instance
    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }


    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            // save transaction details to file
            saveTransaction(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public boolean returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails);
            return true;  // Return true if return is successful
        } else {
            System.out.println("This book was not borrowed by the member.");
            return false;  // Return false if the book was not borrowed by the member
        }
    }

 // save transaction details to file
   private void saveTransaction(String transactionDetails) {
	   try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaction.txt", true))){
		   writer.write(transactionDetails);
		   writer.newLine();
	   } catch (IOException e) {
		   System.out.println("Error saving transaction: " + e.getMessage());
	   }
   }
   
   // Add display transaction History method
   public void displayTransactionHistory() {
	   File file = new File("transaction.txt");
	   if(!file.exists()) {
		   System.out.println("No transaction history available.");
		   return;
	   }
	   System.out.println("\n--- Transaction History ---");
	   try (BufferedReader reader = new BufferedReader(new FileReader(file))){
		   String line;
		   while ((line = reader.readLine()) != null) {
			   System.out.println(line);
		   }
	   } catch (IOException e) {
		   System.out.println("Error reading transaction history: " + e.getMessage());
	   }
	   System.out.println("-----------\n");
   }
    // Get the current date and time in a readable format
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}