package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	private String helpMessage = "SHOWMYACCOUNTS - Shows a list of " +
			"your accounts along with their current balances\n" +
			"NEWACCOUNT <Name> - Creates a new account with the name " +
			"specified (e.g. 'NEWACCOUNT Savings')\n" +
			"REMOVEACCOUNT <Name> - Deletes an existing account " +
			"(e.g. 'REMOVEACCOUNT Savings')\n" +
			"MOVE <Amount> <From> <To> - Moves funds between your " +
			"accounts (e.g. 'MOVE 100.00 Main Savings')\n" +
			"PAY <Amount> <FromAccount> <Person/Company> <ToAccount> - " +
			"Moves funds from one of your accounts to the account of " +
			"another user (e.g. 'PAY 100.00 Main Christina Savings')";
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = new NewBank(DefaultCustomers.create());
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			String invalid = "Invalid";
			String invalidUsername = "Invalid username";
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			boolean isLoggedIn  = false;
			int attempts = 0;
			CustomerID customer = new CustomerID (CustomerID.generateCustomerID()); //assigns random 6-digit ID
			// ask for password
			while (!isLoggedIn && attempts<5) {
				out.println("Enter Password");
				String password = in.readLine();
				out.println("Checking Details...");
				// authenticate user and get customer ID token from bank for use in subsequent requests
				customer = bank.checkLogInDetails(userName, password);
				attempts++;
				if (!customer.getKey().contains(invalid)) {
					isLoggedIn = true;
				} else if(customer.getKey().contains(invalidUsername)) {
					break;
				}
			}
				// if the user is authenticated then get requests from the user and process them
			if (!customer.getKey().contains(invalid)) {
				out.println("\nLog In Successful for " + customer.getKey() + ". What do you want to do?");
				while (true) {
					//display options to the user
					out.println("1 - View the list of available commands\n" +
								"2 - Enter command(s)\n" +
								"3 - Log out\n" +
								"4 - Exit NewBank");
					int choice = Integer.parseInt(in.readLine());
					if (choice == 1) {
						out.println(helpMessage);
						out.println("Press Enter to continue...");
						in.readLine();
					}
					else if (choice == 2) {
						out.println("Enter command: ");
						String request = in.readLine();
						out.println("Request from " + customer.getKey());
						String response = bank.processRequest(customer, request);
						out.println(response);
					}
					else if (choice == 3) {
						out.println("Log out functionality to be implemented");
					}
					else if (choice == 4) {
						out.println("Thank you for using New Bank!");
						System.exit(0); //need to implement a proper exit

					}
					else out.println("Invalid input!");
				}
			}
			else {
				out.println(customer.getKey());
				out.println("Log In Failed");
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}
