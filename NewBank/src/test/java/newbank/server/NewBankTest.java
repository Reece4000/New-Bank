package newbank.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class NewBankTest {

    @DisplayName("User can view their accounts")
    @Test
    void testShowAccount() {
        NewBank testBank =  new NewBank(DefaultCustomers.create());
        String testRequest = testBank.processRequest(new CustomerID("Bhagy"), "SHOWMYACCOUNTS");
        Assertions.assertEquals(testRequest, "Main: 1000.0  ");
    }
    @DisplayName("User can login successfully")
    @Test
    void testUserLoginSuccess() {
        NewBank testBank = new NewBank(DefaultCustomers.create());
        CustomerID testCustomer = testBank.checkLogInDetails("Bhagy", "bhag");
        Assertions.assertEquals(testCustomer.getKey(),"Bhagy");
    }
    @DisplayName("User cannot login with unknown username")
    @Test
    void testUserLoginWithUnknownUsername() {
        NewBank testBank = new NewBank(DefaultCustomers.create());
        CustomerID testCustomer = testBank.checkLogInDetails("Chagy", "bhag");
        Assertions.assertEquals(testCustomer.getKey(),"Invalid username");
    }
    @DisplayName("User cannot login with unknown password")
    @Test
    void testUserLoginWithUnknownPassword() {
        NewBank testBank = new NewBank(DefaultCustomers.create());
        CustomerID testCustomer = testBank.checkLogInDetails("Bhagy", "bag");
        Assertions.assertEquals(testCustomer.getKey(),"Invalid password");
    }
}
