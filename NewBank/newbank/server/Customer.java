package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	public static boolean isPasswordValid(string password){
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
			else if(Character.isDigit(ch)){
				continue;
			}
			else if(Character.isDigit(ch)){
				hasNumber=true;
			}
			else{
				hasSpecialChar=true;
			}
		}
		if(hasSpecialChar == true && hasSpecialChar == true && hasNumber ==true)
         System.out.println("\nThe Password is Strong.");
         else
         System.out.println("\nThe Password is Weak.");
		
		
		return hasSpecialChar && hasUpperChar && hasNumber;
	}

}
