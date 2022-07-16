package newbank.server;

import org.junit.jupiter.api.Test;


class NewBankTest {

    @Test
    void getBank() {
    }

    @Test
    void checkLogInDetails() {
    }

    @Test
    void processRequest() {
        NewBank testBank =  NewBank.getBank();
        testBank.processRequest()
    }
}
