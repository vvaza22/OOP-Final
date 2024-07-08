package Account;

import Database.Database;
import Database.DatabaseCredentials;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.*;

class AccontManagerTest {
    private AccountManager accountManager;
    private Database db;
    private Account acc0, acc1, acc2;

    @BeforeEach
    public void setUp(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        accountManager = new AccountManager(db);

        try {
            Connection con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table users");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        acc0 = new Account(
                0,
                "saxeli0",
                "gvari0",
                "username0",
                "/images/default.jpg",
                Hash.hashPassword("paroli0"),
                "me0",
                "user"
        );


        acc1 = new Account(
                1,
                "saxeli1",
                "gvari1",
                "username1",
                "/images/default.jpg",
                Hash.hashPassword("paroli1"),
                "me1",
                "user"
        );

        acc2 = new Account(
                2,
                "saxeli2",
                "gvari2",
                "username2",
                "/images/de1fault.jpg",
                Hash.hashPassword("paroli2"),
                "me2",
                "admin"
        );
        accountManager.registerAccount(acc0);
        accountManager.registerAccount(acc1);
        accountManager.registerAccount(acc2);
    }

    @Test
    public void testAccExists(){
        assertTrue(accountManager.accountExists("username0"));
        assertTrue(accountManager.accountExists("username1"));
        accountManager.removeAccount("username0");
        assertFalse(accountManager.accountExists("username0"));
    }

    @Test
    public void testPasswordCheck(){
        assertTrue(accountManager.passwordMatches("username0", "paroli0"));
        assertFalse(accountManager.passwordMatches("username1", "paroli0"));
    }

    @Test
    public void testAccountById(){
        int id0 = acc0.getUserId();
        int id1 = acc1.getUserId();

        Account account0 = accountManager.getAccountById(id0);
        Account account1 = accountManager.getAccountById(id1);

        assertNull(account0); // since acc0 id cant be 0 it adds as id1
        assertEquals(acc0.getFirstName(), account1.getFirstName());
    }

    @Test
    public void testGetAccount(){
        String acc1UserName = acc1.getUserName();
        Account account1 = accountManager.getAccount(acc1UserName);
        assertTrue(account1.getFirstName().equals(acc1.getFirstName()));

        String acc2UserName = acc2.getUserName();
        Account account2 = accountManager.getAccount(acc2UserName);
        assertTrue(account2.getFirstName().equals(acc2.getFirstName()));
    }

    @Test
    public void testUpdateAboutMe(){
        String acc1UserName = acc1.getUserName();
        String updateAboutMe1 = "aboutMe1";
        acc1.setAboutMe(updateAboutMe1);
        accountManager.updateAboutMeAccount(acc1);
        Account account1 = accountManager.getAccount(acc1UserName);
        assertTrue(account1.getAboutMe().equals(acc1.getAboutMe()));
    }

    @Test
    public void testUpdateImage(){
        String acc2UserName = acc2.getUserName();
        String updateImageLink2 = "/images/sample_1.jpg";
        acc2.setImage(updateImageLink2);
        accountManager.updateProfilePictureAccount(acc2);
        Account account2 = accountManager.getAccount(acc2UserName);
        assertTrue(account2.getImage().equals(acc2.getImage()));
    }

    @Test
    public void testGetAccounts(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        accounts.add(acc0);
        accounts.add(acc1);
        accounts.add(acc2);
        ArrayList<Account> data = accountManager.getAccounts();
        boolean checker = true;
        for(int i=0; i<data.size(); i++){
            if(!data.get(i).getUserName().equals(accounts.get(i).getUserName())){
                checker = false;
                break;
            }
        }
        assertEquals(true, checker);
    }

}
