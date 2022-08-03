package newbank.server;
import java.util.Random;

public class CustomerID {
	private String key;

	public static String generateCustomerID() {
		// generates a 6 digit random number, for the customer ID
		Random r = new Random();
		int number = r.nextInt(999999);
		return String.format("%06d", number);
	}

	public CustomerID(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
