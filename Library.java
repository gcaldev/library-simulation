package Books;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;


public class Library {

	private HashMap<String, Author> allAuthors = new HashMap<String, Author>();
	private HashMap<String, Book> allBooks = new HashMap<String, Book>();
	private double earnings = 0;
	
	public boolean loadAuthors(String dir) {
	    try {
	        File selectedFile = new File(dir);
	        Scanner reader = new Scanner(selectedFile);
	        
	        
	        while (reader.hasNextLine()) {
	          String data = reader.nextLine();
	          String[] parsedData= data.split(",");
	          char actualGender= parsedData[2].charAt(0);
	          Author actualAuthor = new Author(parsedData[0],parsedData[1],actualGender);
	          allAuthors.put(parsedData[0], actualAuthor);
	        }
	        
	        reader.close();
	        return true;
	      } catch (FileNotFoundException e) {
	        return false;
	      }

	}
	
	public boolean loadBooks(String dir) {
	    try {
	        File selectedFile = new File(dir);
	        Scanner reader = new Scanner(selectedFile);
	        
	        
	        while (reader.hasNextLine()) {
	          String data = reader.nextLine();
	          String[] parsedData= data.split(",");
	          String bookName = parsedData[0];
	          String[] authors = parsedData[1].split(";");
	          double price = Double.parseDouble(parsedData[2]);
	          int qty = Integer.parseInt(parsedData[3]);
	          
	          Author[] foundAuthors = new Author[authors.length];
	          for(int i = 0; i < authors.length; i++) {
	        	  if(allAuthors.containsKey(authors[i])) {
	        		  foundAuthors[i] = allAuthors.get(authors[i]); // Checks if loaded authors are valid and get their instances.
	        	  }
	          }
	          Book actualBook = new Book(bookName, foundAuthors, price, qty);
	          allBooks.put(parsedData[0], actualBook);
	        }
	        
	        reader.close();
	        return true;
	      } catch (FileNotFoundException e) {
	        return false;
	      }
	}
	
	
	public Book findBook(String bookName) {
		return allBooks.containsKey(bookName) ? allBooks.get(bookName) : null;
	}
	
	public Author findAuthor(String authorName) {
		return allAuthors.containsKey(authorName) ? allAuthors.get(authorName) : null;	
	}
	
	public void addBook(String newName, Author[] newAuthors, double newPrice, int newQty) {
		if(!allBooks.containsKey(newName)) {
			Book newBook = new Book(newName, newAuthors, newPrice, newQty);
			allBooks.put(newName, newBook);
		}
	}
	
	public void addAuthor(String newName, String newEmail, char newGender) {
		if(!allAuthors.containsKey(newName)) {
			Author newAuthor = new Author(newName, newEmail, newGender);
			allAuthors.put(newName, newAuthor);
		}
	}
	
	public double sellBook(String bookName) {
		Book actualBook = findBook(bookName);
		if(actualBook == null) return 0;//Means that the book was not found.
		double newIncome = actualBook.getPrice();
		earnings+=newIncome;
		int actualQty = actualBook.getQty() - 1;
		actualBook.setQty(actualQty);
		if(actualQty < 1) {			
			allBooks.remove(bookName);
		}
		return earnings;
	}
	
	public double showEarnings() {
		return earnings;
	}
	
	public Book[] showAllBooks() {
		Book[] result = new Book[allBooks.size()];
		int i = 0;
		for(Book actualBook : allBooks.values()) {
			result[i] = actualBook;
			i++;
		}
		return result;
	}
}
