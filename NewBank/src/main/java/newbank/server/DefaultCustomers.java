package newbank.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DefaultCustomers {

    public static HashMap<String, Customer> create() {
        HashMap<String, Customer> customers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("default-customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Customer customer = new Customer(values[1]);
                customer.addAccount(new Account(values[2], Double.parseDouble(values[3])));
                customers.put(values[0], customer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
