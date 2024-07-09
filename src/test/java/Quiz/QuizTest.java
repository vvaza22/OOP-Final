package Quiz;

import Account.Account;
import Question.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.*;

public class QuizTest {
    private Quiz q;
    private ArrayList<Question> qList;

    @BeforeEach
    public void setUp(){

        qList = new ArrayList<>();

        ArrayList<String> aList1 = new ArrayList<>();
        aList1.add("A");
        aList1.add("B");
        aList1.add("C");
        Question q1 = new QuestionResponse("Question 1", 1, aList1);

        ArrayList<String> aList2 = new ArrayList<>();
        aList2.add("M");
        aList2.add("N");
        Question q2 = new QuestionResponse("Question 2", 2, aList2);

        qList.add(q1);
        qList.add(q2);

        q = new Quiz(
                1,
                "Test Quiz",
                1,
                "Test Desc",
                "some_image.jpg",
                true,
                true,
                false,
                1,
                "2024",
                qList
        );
    }

    @Test
    public void testGetObjects(){
        String image = q.getImage();
        assertTrue(image.equals("some_image.jpg"));

        int id = q.getId();
        assertTrue(id == 1);

        String name = q.getName();
        assertTrue(name.equals("Test Quiz"));

        int authorId = q.getAuthorId();
        assertTrue(authorId == 1);

        boolean practiceAllowed = q.isPracticeAllowed();
        assertTrue(practiceAllowed);

        String dateOfCreation = q.getCreateTime();
        assertTrue(dateOfCreation.equals("2024"));

        boolean isRandomized = q.isRandomized();
        assertTrue(isRandomized);

        boolean isImmCorrOn = q.isImmediateCorrectionOn();
        assertFalse(isImmCorrOn);

        int display = q.getDisplayMode();
        assertEquals(display, 1);

        String description = q.getDescription();
        assertTrue(description.equals("Test Desc"));

        int numOfQuestion = q.getNumberOfQuestions();
        assertEquals(numOfQuestion, qList.size());

        ArrayList<Question> questions = q.getQuestions();
        assertTrue(questions.size() == qList.size());

        boolean check = true;
        for(int i=0; i<qList.size(); i++){
            Question question1 = questions.get(i);
            Question data1 = qList.get(i);
            if(question1.getId() != data1.getId()){
                check = false;
                break;
            }
        }
        assertTrue(check);
    }

    @Test
    public void test(){
        boolean hasQuestion = q.hasQuestion(1);
        assertTrue(hasQuestion);

        hasQuestion = q.hasQuestion(3);
        assertFalse(hasQuestion);

        Question ques1 = q.getQuestionById(1);
        Question ques2 = q.getQuestionById(2);

        int finalScore = q.countScore();
        assertEquals(0, finalScore);

        int maxScore = q.getMaxScore();
        assertEquals(2, maxScore);
    }
}
