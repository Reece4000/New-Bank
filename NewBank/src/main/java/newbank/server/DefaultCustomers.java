package newbank.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DefaultCustomers {

    public static HashMap<String, Customer> create() {
        HashMap<String, Customer> customers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/default-customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String password = values[1];
                String accountName = values[2];
                String customerID = values[0];
                double openingBalance = Double.parseDouble(values[3]);
                Customer customer = new Customer(password);
                customer.addAccount(new Account(accountName, openingBalance));
                customers.put(customerID, customer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
