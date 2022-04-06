package Books;

public class Book {

	private Author[] authors;
	private String name;
	private int qty = 0;
	private double price;
	
	
	public Book (String newName, Author[] newAuthors, double newPrice) {
		authors = newAuthors;
		name = newName;
		price = newPrice;
	}

	public Book (String newName, Author[] newAuthors, double newPrice, int newQty) {
		authors = newAuthors;
		name = newName;
		price = newPrice;
		qty = newQty;
	}

	
	public String getName() {
		return name;
	}

	public Author[] getAuthors() {
		return authors;
	}
	
	public String getAuthorNames() {
		String result = "";
		for(int i = 0; i < authors.length; i++) {
			Author actual = authors[i];
			result = result + actual.getName() + ",";
		}
		return result.substring(0,result.length() - 1);
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double newPrice) {
		price = newPrice;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int newQty) {
		qty = newQty;
	}
	
	public String toString() {
		String part1 = "Book[name=" + name + ",author={";
		String part2 = "},price=" + price + ",qty=" + qty + "]";
		for(int i = 0; i < authors.length; i++) {
			String x = authors[i].toString();
			part1 = part1.concat(x+",");
		}
		return part1.substring(0, part1.length()-1) + part2;
	}
}
