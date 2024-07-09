package Announcements;

import Question.FillBlank;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AnnoTest extends TestCase {
    private Announcement anno1;
    private Announcement anno2;
    @Test
    public void testVars1() {
        anno1 = new Announcement(1, "Anno1", "2024-05-08", "first anno", 1, 10, 4);

        assertEquals("Anno1", anno1.getTitle());
        assertEquals("2024-05-08", anno1.getDate());
        assertEquals(1, anno1.getId());
        assertEquals(10, anno1.getNumLike());
        assertEquals(4, anno1.getNumDislike());
        assertEquals("first anno", anno1.getBody());
        assertEquals("2024-05-08", anno1.getCreateTime());
        assertEquals(1, anno1.getAuthorId());
    }

    @Test
    public void testVars2() {
        anno2 = new Announcement(2, "Anno2", "2024-05-09", "second anno", 2, 20, 2);

        assertEquals("Anno2", anno2.getTitle());
        assertEquals("2024-05-09", anno2.getDate());
        assertEquals(2, anno2.getId());
        assertEquals(20, anno2.getNumLike());
        assertEquals(2, anno2.getNumDislike());
        assertEquals("second anno", anno2.getBody());
        assertEquals("2024-05-09", anno2.getCreateTime());
        assertEquals(2, anno2.getAuthorId());
    }
}
