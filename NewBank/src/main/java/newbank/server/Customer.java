package newbank.server;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer {
	
	private ArrayList<Account> accounts;

	public String storedPassword;
	String email;
	String fullName;
	String userName;
	String address;
	String dateOfBirth;
	//String customerID;

	public Customer(String fullName, String userName, String password, String email, String dateOfBirth, String address) {
		accounts = new ArrayList<>();
		storedPassword = password;
		this.userName = userName;
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
	}

	public static boolean writeCustomerData(final Customer newCustomer, final String newCustomerData) {
		try {
			Writer customerList = new BufferedWriter(new FileWriter("NewBank/src/main/java/newbank/server/customers.txt", true));
			customerList.append(newCustomerData + "\n");
			customerList.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static HashMap<String, Customer> create() {
		HashMap<String, Customer> customers = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Customer.class.getResourceAsStream("customers.txt")))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("/");
				String fullName = values[0];
				String userName = values[1];
				String password = values[2];
				String email = values[3];
				String DoB = values[4];
				String address = values[5];
				String accountName = values[6];
				double openingBalance = Double.parseDouble(values[7]);
				Customer customer = new Customer(fullName, userName, password, email, DoB, address);
				customer.addAccount(new Account(accountName, openingBalance));
				customers.put(userName, customer);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return customers;
	}


	/* Loop through accounts names converting to string
	* format, if more than 1 account add delimiter in
	* to print in a nicer format.
	 */
	public String accountsToString() {
		String s = "";
		String delimiter = "";

		if(accounts.size()>1){
			delimiter = Character.toString((char)124);
		}

		for(Account a : accounts) {
			s += a.toString()+" "+delimiter+" ";
		}
		return s;
	}

	/* loop through customer accounts,
	* if name matches return account else null
	 */
	public Account getAccount(String accountName){
		for(Account a : accounts){
			if(a.getName().equals(accountName)){
				return a;
			}
		}
		return null;
	}


	public void addAccount(Account account) {
		accounts.add(account);		
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
  	}

	public String getStoredPassword() {
	    return storedPassword;
	}

	public static boolean isPasswordValid(String password){
		int minimumPasswordLength = 8;
		int passwordLength = password.length();

		if (passwordLength < minimumPasswordLength){
			return false;
		}
		boolean hasSpecialChar = false;
		boolean hasUpperChar = false;
		boolean hasNumber = false;

		for(int i=0; i< passwordLength; i++){
			char ch = password.charAt(i);
			if(Character.isUpperCase(ch)){
				hasUpperChar = true;
			}
			else if(Character.isLowerCase(ch)){
				continue;
			}
			else if(Character.isDigit(ch)){
				hasNumber=true;
			}
			else{
				hasSpecialChar=true;
			}
		}
		boolean isPasswordValid = hasSpecialChar && hasUpperChar && hasNumber;

		return isPasswordValid;
	}
	public String toString(){
		return "Customer: \n Full name: "+this.fullName+"\n Address: "+this.address+"\n Email: "+this.email;
	}
}
