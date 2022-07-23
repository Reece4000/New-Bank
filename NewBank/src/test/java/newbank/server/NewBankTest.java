package src.test.java.newbank.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.main.java.newbank.server.CustomerID;
import src.main.java.newbank.server.DefaultCustomers;
import src.main.java.newbank.server.NewBank;


class NewBankTest {

    @Test
    void testShowAccount() {
        NewBank testBank =  new NewBank(DefaultCustomers.create());
        String testRequest = testBank.processRequest(new CustomerID("Bhagy"), "SHOWMYACCOUNTS");
        Assertions.assertEquals(testRequest, "Main: 1000.0  ");
    }
}
