package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class NewBankClientHandler extends Thread {

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
		bank = new NewBank(Customer.create());
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

  /*
  Create the register and login methods within the clienthandler class
  Create a method to write customer data to the txt file
  Amend default customers list and java class to include additional fields
  Add new command to show details of the customer - SHOWCUSTOMERINFO
   */


	private boolean newRegistration() { //encapsulate new customer registration as a separate method
		boolean isRegistered = false;
		while (!isRegistered) {
			try {
				out.println("Please enter your full name: ");
				String customerFullName = in.readLine();

				out.println("Please enter a secure password: ");
				String pass = in.readLine();
				while (!Customer.isPasswordValid(pass)) {
					out.println("The password you have entered is not valid, please try again - note that passwords must be " +
							"8 at least characters long, and must contain a mix of upper and lower case " +
							"characters, at least one digit, and at least one special character: ");
					pass = in.readLine();
				}

				out.println("Please enter a contact email address: ");
				String newUserEmail = in.readLine();
				while (!newUserEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
					out.println("Invalid email address! Please try again: ");
					newUserEmail = in.readLine();
				}

				out.println("Please enter your date of birth (e.g. '13.08.1992')");
				String newUserDOB = in.readLine();
				while (!newUserDOB.matches("^(?:0[1-9]|[12]\\d|3[01])([\\/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$")) {
					out.println("Invalid date! Please try again and ensure that the formatting is correct (dd.mm.yyyy)");
					newUserDOB = in.readLine();
				}

				out.println("please enter your primary address, (street number, street, city, zipcode)");
				String newUserAddress = in.readLine();

				out.println("Create a user name: ");
				String newUserName = in.readLine();
				//String customerID = CustomerID.generateCustomerID();
				Customer newCustomer = new Customer(customerFullName, newUserName, pass, newUserEmail, newUserDOB, newUserAddress);

				if (Customer.attemptWriteCustomerData(newCustomer, customerFullName + "/" +
						newUserName + "/" + pass + "/" + newUserEmail + "/" + newUserDOB + "/" + newUserAddress +
						"/" + "Main" + "/" + "0.00")) {
					this.bank.getAccounts().put(newUserName, newCustomer);
					out.println("Registration successful!");
					isRegistered = true;
					Customer.create();
				} else
					out.println("An error occurred - new customer details have not been stored. Please try again.");
			} catch (IOException e) {
				out.println("Input error!");
				continue;
			}
		}
		return isRegistered;

	}

	private CustomerID logIn() {
		try {
			String invalid = "Invalid";
			String invalidUsername = "Invalid username";
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			boolean isLoggedIn = false;
			int attempts = 0;
			// ask for password
			while (!isLoggedIn && attempts < 5) {
				out.println("Enter Password");
				String password = in.readLine();
				out.println("Checking Details...");
				// authenticate user and get customer ID token from bank for use in subsequent requests
				CustomerID customer = bank.checkLogInDetails(userName, password);
				attempts++;
				if (!customer.getKey().contains(invalid)) {
					return customer;
				} else if (attempts == 5) {
					out.println("Too many attempts!");
					// exit program
					return null;
				} else if (customer.getKey().contains(invalidUsername)) {
					return null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void run() {
		// keep getting requests from the client and processing them
		CustomerID customer = null;
		int signInStatus = 0;
		try {
			while (signInStatus == 0) {
			out.println("What would you like to do? \n1 - Register\n2 - Log in");
				signInStatus = Integer.parseInt(in.readLine());
			while (signInStatus == 1) {
				if(newRegistration()) {
					signInStatus = 0;
					}
				}
			}
			if (signInStatus == 2) {
				customer = logIn();
				if (customer == null) {
					out.println("Log in failed.");
					signInStatus = 0;
				} else {
					out.println("\nLog In Successful for " + customer.getKey() + ". What do you want to do?");
				}
				// if the user is authenticated then accept & process requests
					while (signInStatus == 2) {
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
						} else if (choice == 2) {
							out.println("Enter command: ");
							String request = in.readLine();
							out.println("Request from " + customer.getKey());
							String response = bank.processRequest(customer, request);
							out.println(response);
						} else if (choice == 3) {
							signInStatus = 0;
							//out.println("Log out functionality to be implemented");
						} else if (choice == 4) {
							out.println("Thank you for using New Bank!");
							System.exit(0); //need to implement a proper exit

						} else
							out.println("Invalid input!");
					}
				} else {
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