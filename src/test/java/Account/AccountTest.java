package Account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void test1() {
        Account ac1 = new Account(0,
                "Giorgi",
                "Pirveli",
                "g.pirveli",
                "link1",
                Hash.hashPassword("g.pirveli"),
                "Hi, I hate Boston Celtics",
                "admin"
        );
        assertTrue(ac1.checkPassword("g.pirveli"));
    }


}