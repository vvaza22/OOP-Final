package Achievement;

import Achievement.Achievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementTest {

    Achievement achi1, achi2, achi3, achi4;

    @BeforeEach
    public void setUp(){
        achi1 = new Achievement(1, Achievement.ROOKIE_AUTHOR);
        achi2 = new Achievement(2, Achievement.PRACTITIONER);
        achi3 = new Achievement(3, Achievement.QUIZ_SLAYER);
        achi4 = new Achievement(4, Achievement.LORD_OF_THE_QUIZZES);
    }

    @Test
    public void testNotNull() {
        assertNotNull(achi1);
        assertNotNull(achi2);
        assertNotNull(achi3);
        assertNotNull(achi4);
    }

    @Test
    public void testGetUserId(){
        assertEquals(1, achi1.getUserId());
        assertEquals(2, achi2.getUserId());
        assertEquals(3, achi3.getUserId());
        assertEquals(4, achi4.getUserId());
    }

    @Test
    public void testGetType(){
        assertEquals(achi1.getType(), Achievement.ROOKIE_AUTHOR);
        assertEquals(achi2.getType(), Achievement.PRACTITIONER);
        assertEquals(achi3.getType(), Achievement.QUIZ_SLAYER);
        assertEquals(achi4.getType(), Achievement.LORD_OF_THE_QUIZZES);
    }

}
