package Announcements;

import Account.*;
import Database.Database;
import Database.DatabaseCredentials;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnnoManagerTest extends TestCase {
    private Database db;
    private AnnouncementManager anm;
    private Announcement anno1, anno2;
    private Account acc1;
    private AccountManager amgr;

    @BeforeEach
    public void setUp() {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+ DatabaseCredentials.DB_HOST+"/"+ DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        anm = new AnnouncementManager(db);

        try {
            Connection con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table users");
            stmt.executeUpdate("truncate table anno");
            stmt.executeUpdate("truncate table reaction");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Add User
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
        amgr = new AccountManager(db);
        amgr.registerAccount(acc1);

        anno1 = new Announcement(1, "Anno1", "2024-05-08", "first anno", acc1.getUserId(), 10, 4);
        anno2 = new Announcement(2, "Anno2", "2024-05-09", "second anno", acc1.getUserId(), 20, 2);
    }


    @Test
    public void testAddAndGetAnnouncement() {
        anm.addAnnouncement(anno1);
        anm.addAnnouncement(anno2);
        ArrayList<Announcement> annos = anm.getAnnouncements();
        assertEquals(2, annos.size());
        assertEquals(anno1.getId(), anm.getAnnouncement(1).getId());
        assertNull(anm.getAnnouncement(5));
    }

    @Test
    public void testReactions() {
        anm.addAnnouncement(anno1);
        anm.reactAnnouncement(anno1.getId(), acc1.getUserId(), "LIKE");
        assertEquals("LIKE", anm.getReaction(1, anno1.getId()));

        anm.deleteReaction(anno1.getId(), 1);
        assertNull(anm.getReaction(1, anno1.getId()));
    }

}
