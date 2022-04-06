package Books;
import java.util.Scanner;

/*
 * Se encarga de interactuar directamente con el usuario y el resto de objetos.
 * 
 */

public class LibraryGUI {
	public static final String[] comandos = {"ver_libros","vender_libro","ver_libro","ver_autor","ingresos", "agregar_libro", "agregar_autor", "salir"};
	private Library lib = new Library();
	private Scanner inputDetector;
	
	public LibraryGUI(Scanner in) {
		inputDetector = in;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
	    
		LibraryGUI Interface = new LibraryGUI(in);
		
		Interface.askLoadDirs();

		while(true) {
			System.out.print("Ingrese la operacion a realizar: ");
			String command = in.nextLine();
			if(command.equals("ver_libros")) Interface.displayBooks();
			else if(command.equals("vender_libro")) Interface.displaySell();
			else if(command.equals("ver_libro")) Interface.displayBook();
			else if(command.equals("ingresos")) Interface.displayIncome();
			else if(command.equals("ver_autor")) Interface.displayAuthor();			
			else if(command.equals("agregar_autor")) Interface.displayAddAuthor();						
			else if(command.equals("agregar_libro")) Interface.displayAddBook();						
			else if(command.equals("salir")) {
				System.out.println("La sesión se cerró exitosamente.");
				break;
			}
		}
		in.close();
	}
	
	public void askLoadDirs() {	    
	    boolean loadSuccess = false;
	    while(!loadSuccess) {
	    	System.out.print("Ingrese la direccion del archivo que contenga la informacion de los autores: ");
	    	String actualDir = inputDetector.nextLine();
	    	loadSuccess = lib.loadAuthors("src/Books/"+ actualDir);
	    }
	    
	    loadSuccess = false;
	    
	    System.out.println("Se cargo exitosamente el archivo");
	    
	    while(!loadSuccess) {
	    	System.out.print("Ingrese la direccion del archivo que contenga la informacion de los libros: ");
	    	String actualDir = inputDetector.nextLine();
	    	loadSuccess = lib.loadBooks("src/Books/"+ actualDir);
	    }

	    System.out.println("Se cargo exitosamente el archivo");
		System.out.println("--------------------------------");
		System.out.println("Comandos disponibles para usar la bibloteca: ");
		
		for(String comando : comandos) {
			System.out.println(comando);
		}			
		
		System.out.println("--------------------------------");
	}
	
	public void displayBooks() {
		Book[] allBooks = lib.showAllBooks();
		System.out.print("\n\nActualmente la bibloteca dispone de los siguientes libros:\n\n");

		for(Book book : allBooks) {
			System.out.println(book.getName() + " by " + book.getAuthorNames());
		}
	}
	
	public void displayBook() {
		System.out.print("Ingrese el nombre del libro que busca: ");
		String bookName = inputDetector.nextLine();
		Book book = lib.findBook(bookName);
		if(book == null) {
			System.out.println("No se encontró el libro que buscabas.");
		}
		else {			
		System.out.println("	Titulo: " + book.getName());
		System.out.println("	Autores: " + book.getAuthorNames());
		System.out.println("	Precio: " + book.getPrice());
		System.out.println("	Stock: " + book.getQty());
		}
	}
	
	public void displaySell() {
		System.out.print("Ingrese el nombre del libro que desea vender: ");
		String bookName = inputDetector.nextLine();
		Book book = lib.findBook(bookName);
		if(book == null) System.out.println("No se encontró el libro que buscabas.");
		else {
			lib.sellBook(bookName);
			System.out.println("Se vendio el libro titulado " + bookName + "a un precio de " + book.getPrice() + "[Unidades restantes : " + book.getQty() + "]");
		}
	}
	
	public void displayIncome() {
		System.out.println("La bibloteca generó " + lib.showEarnings() + "hasta el momento.");
	}
	
	public void displayAuthor() {
		System.out.print("Ingrese el nombre del autor: ");
		String authorName = inputDetector.nextLine();
		Author author = lib.findAuthor(authorName);
		if(author == null) System.out.println("No se encontró el autor que buscabas.");
		else {
			System.out.println("	Nombre completo: " + authorName);
			System.out.println("	Email: " + author.getEmail());
			System.out.println("	Genero: " + (author.getGender() == 'm' ? "Masculino" : "Femenino"));	
		}

	}

	public void displayAddAuthor() {
		while(true) {			
			System.out.print("\n\nAgregar autor\n\n\n");
			System.out.print("Ingrese el nombre completo: ");
			String authorName = inputDetector.nextLine();
			if(lib.findAuthor(authorName) == null) {
				System.out.print("Ingrese el email: ");
				String email = inputDetector.nextLine();
				System.out.print("Ingrese el genero[m/f]: ");
				String g = inputDetector.nextLine();

				lib.addAuthor(authorName, email, g.charAt(0));
				System.out.println("Se agrego el autor correctamente.");
				break;
			}
			else {
				System.out.println("Ya existe el autor que se desea agregar.");
				System.out.print("Desea agregar otro autor?[S/N]");
				String choice = inputDetector.nextLine();
				if(choice.equals("N")) break;
			}
		}
	}
	
	public void displayAddBook() {
		System.out.print("\n\nAgregar libro\n\n\n");
		System.out.print("Ingrese el titulo: ");
		String bookName = inputDetector.nextLine();
		if(lib.findBook(bookName) != null) {
			System.out.println("Ya existe un libro con ese titulo");
		}
		else {
			System.out.print("Ingrese los nombres de los autores separados por coma: ");
			String[] authors = inputDetector.nextLine().split(",");
			Author[] foundAuthors = new Author[authors.length];
			for(int i = 0; i < authors.length; i++) {
				Author actualAuthor = lib.findAuthor(authors[i]);
				if(actualAuthor  != null) foundAuthors[i] = actualAuthor;
			}
			System.out.print("Ingrese el precio del libro: ");
			double price = Double.parseDouble(inputDetector.nextLine());
			System.out.print("Ingrese el nuevo stock: ");
			int stock = Integer.parseInt(inputDetector.nextLine());
			lib.addBook(bookName, foundAuthors, price, stock);
			System.out.println("Libro agregado correctamente.");
		}

	}
	
}
