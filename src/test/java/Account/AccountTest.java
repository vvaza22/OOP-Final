package Account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void test1() {
        Account ac1 = new Account(
                "Giorgi",
                "Pirveli",
                "g.pirveli",
                "link1",
                Hash.hashPassword("g.pirveli"));
        assertTrue(ac1.checkPassword("g.pirveli"));

    }


}