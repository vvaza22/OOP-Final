package Anno;

import Announcements.Announcement;
import Announcements.AnnouncementManager;
import Database.Database;
import Database.DatabaseCredentials;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnnoManagerTest {
    @Test
    public void test1() {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+ DatabaseCredentials.DB_HOST+"/"+ DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        Database db = new Database(dataSource);
        AnnouncementManager anm = new AnnouncementManager(db);
        Announcement anno = new Announcement(0, "Anno1", "2024-05-08", "first anno", 1, 10, 4);
        int annoId = anm.addAnnouncement(anno);
        assertEquals(1, annoId);
    }

    @Test
    public void test2() {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+ DatabaseCredentials.DB_HOST+"/"+ DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        Database db = new Database(dataSource);
        AnnouncementManager anm = new AnnouncementManager(db);

        ArrayList<Announcement> list = anm.getAnnouncements();
        assertEquals(1, list.size());
        assertEquals("Anno1", list.get(0).getTitle());

        Announcement an = anm.getAnnouncement(5);
        assertNull(an);
    }

}
