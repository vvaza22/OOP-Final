package Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account acc1, acc2, acc3;

    @BeforeEach
    public void setup(){
        acc1 = new Account(0,
                "Anakin",
                "Skywalker",
                "jedi_knight",
                "/images/default.jpg",
                Hash.hashPassword("padme"),
                "I hate sand",
                "user"
        );

        acc2 = new Account(1,
                "obi-wan",
                "kenobi",
                "jedi master",
                "/images/default.jpg",
                Hash.hashPassword("padawan"),
                "Hello there",
                "admin"
        );

    }

    @Test
    public void testFirstName(){
        String acc1OrigFirstName = acc1.getFirstName();
        assertTrue(acc1OrigFirstName.equals("Anakin"));
        assertFalse(acc1OrigFirstName.equals("anakin"));

        String acc2OrigFirstName = acc2.getFirstName();
        assertTrue(acc2OrigFirstName.equals("obi-wan"));
        assertFalse(acc2OrigFirstName.equals("ben"));
    }

    @Test
    public void testLastName(){
        String acc1OrigLastName = acc1.getLastName();
        assertTrue(acc1OrigLastName.equals("Skywalker"));
        assertFalse(acc1OrigLastName.equals("Vader"));

        String acc2OrigLastName = acc2.getLastName();
        assertTrue(acc2OrigLastName.equals("kenobi"));
        assertFalse(acc2OrigLastName.equals("Kenobi"));
    }

    @Test
    public void testPassHash(){
        String hashedAcc1Pas = "cbace61a82ad3afd5737bc5c78072689";
        assertTrue(acc1.getPassHash().equals(hashedAcc1Pas));

        String hashsedAcc2Pas = "9a4b83013501fde3e8864f4f4b5fb7c5";
        assertTrue(acc2.getPassHash().equals(hashsedAcc2Pas));
    }

    @Test
    public void testUserType(){
        String acc1Type = acc1.getUserType();
        assertTrue(acc1Type.equals("user"));

        String acc2Type = acc2.getUserType();
        assertTrue(acc2Type.equals("admin"));
    }

    @Test
    public void testImage(){
        String imageAcc1 = "/images/default.jpg";
        assertTrue(acc1.getImage().equals(imageAcc1));

        String imageAcc2 = "/images/default.jpg";
        assertTrue(acc2.getImage().equals(imageAcc2));
        imageAcc2 = acc2.setImage("/images/sample_1.jpg");
        assertTrue(acc2.getImage().equals(imageAcc2));
    }

    @Test
    public void testCheckPassword(){
        assertTrue(acc1.checkPassword("padme"));
        assertTrue(acc2.checkPassword("padawan"));
    }

    @Test
    public void testUserId(){
        int acc1Id = acc1.getUserId();
        assertTrue(acc1Id == 0);
        assertFalse(acc1Id == 1);

        int acc2Id = acc2.getUserId();
        assertTrue(acc2Id == 1);
        assertFalse(acc2Id == 0);
    }

    @Test
    public void testAboutMe(){
        String acc1AboutMe = acc1.getAboutMe();
        assertTrue(acc1AboutMe.equals("I hate sand"));
        assertFalse(acc1AboutMe.equals("Lieer!!"));
        acc1AboutMe = acc1.setAboutMe("Lieer!!");
        assertFalse(acc1AboutMe.equals("I hate sand"));
        assertTrue(acc1AboutMe.equals("Lieer!!"));

        String acc2AboutMe = acc2.getAboutMe();
        assertTrue(acc2AboutMe.equals("Hello there"));
        assertFalse(acc2AboutMe.equals("I will do what I must"));
        acc2AboutMe = acc2.setAboutMe("I will do what I must");
        assertFalse(acc2AboutMe.equals("Hello there"));
        assertTrue(acc2AboutMe.equals("I will do what I must"));
    }

    @Test
    public void testAdmin(){
        boolean acc1Admin = acc1.isAdmin();
        assertFalse(acc1Admin);

        boolean acc2Admin = acc2.isAdmin();
        assertTrue(acc2Admin);
    }

    @Test
    public void testUserName(){
        String acc1OrigUserName = acc1.getUserName();
        assertTrue(acc1OrigUserName.equals("jedi_knight"));
        assertFalse(acc1OrigUserName.equals("jedi master"));

        String acc2OrigUserName = acc2.getUserName();
        assertTrue(acc2OrigUserName.equals("jedi master"));
        assertFalse(acc2OrigUserName.equals("jedi knight"));
    }

    @Test
    public void testValidName(){
        String acc1OrigUserName = acc1.getUserName();
        assertTrue(acc1.isValidUsername(acc1OrigUserName));

        String acc2OrigUserName = acc2.getUserName();
        assertFalse(acc2.isValidUsername(acc2OrigUserName));
    }
}