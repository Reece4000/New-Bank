package newbank.server;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {
	
	private ArrayList<Account> accounts;

	public String storedPassword;
	String email;
	String fullName;
	String address;
	String dateOfBirth;

	public Customer(String fullName, String password, String email, String dateOfBirth, String address) {
		accounts = new ArrayList<>();
		storedPassword = password;
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.dateOfBirth = dateOfBirth;

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
                
		if(isPasswordValid){
			System.out.println("\nThe Password is Strong.");
		}
		else{
                        System.out.println("\nThe Password is Weak.");
		}
		return isPasswordValid;
	}

	public String toString(){
		 return "Customer: \n Full name: "+this.fullName+"\n Address: "+this.address+"\n Email: "+this.email;
	}
}
