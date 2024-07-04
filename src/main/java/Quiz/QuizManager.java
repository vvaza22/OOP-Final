package Quiz;

import Database.Database;
import Question.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizManager {
    private final Database db;

    public QuizManager(Database db) {
        this.db = db;
    }

    public Quiz getQuiz(int id) {
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from quiz where quiz_id=?"
            );
            stmt.setInt(1, id);

            // Create Quiz Object
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Quiz quiz = new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("randomize"),
                        rs.getBoolean("practice_mode"),
                        rs.getBoolean("immediate_correction"),
                        rs.getString("display_type").equals("ONE_PAGE") ? Quiz.ONE_PAGE : Quiz.MULTIPLE_PAGES,
                        getQuestions(id, con)
                );

                // Close connection to the database
                stmt.close();
                con.close();

                return quiz;
            }

            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Quiz does not exist
        return null;
    }

    private ArrayList<Question> getQuestions(int quizId, Connection con) {
        ArrayList<Question> questionList = new ArrayList<Question>();

        try {
            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from questions where quiz_id=? order by question_order asc"
            );
            stmt.setInt(1, quizId);

            // Retrieve questions
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                switch(rs.getInt("question_type")) {
                    case QuestionType.QUESTION_RESPONSE:
                        QuestionResponse questionResponse = new QuestionResponse(
                                rs.getString("question_text"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                        questionList.add(questionResponse);
                        break;
                    case QuestionType.FILL_BLANK:
                        FillBlank fillBlank = new FillBlank(
                                rs.getString("question_text"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                        questionList.add(fillBlank);
                        break;
                    case QuestionType.MULTIPLE_CHOICE:
                        ArrayList<Choice> choiceList = getChoiceList(rs.getInt("question_id"), con);
                        MultipleChoice multipleChoice = new MultipleChoice(
                                rs.getString("question_text"),
                                choiceList,
                                getCorrectIndex(choiceList)
                        );
                        questionList.add(multipleChoice);
                        break;
                    case QuestionType.PICTURE_RESPONSE:
                        PictureResponse pictureResponse = new PictureResponse(
                                rs.getString("question_text"),
                                rs.getString("picture"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                }
            }

            stmt.close();

        } catch(SQLException ignored) {}

        return questionList;
    }

    private ArrayList<String> getTextAnswerList(int questionId, Connection con) {
        ArrayList<String> textAnswerList = new ArrayList<String>();
        try {
            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from text_answers where question_id=?"
            );
            stmt.setInt(1, questionId);

            // Retrieve questions
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                textAnswerList.add(rs.getString("answer_value"));
            }

            stmt.close();
        } catch(SQLException e) {

        }

        return textAnswerList;
    }

    private ArrayList<Choice> getChoiceList(int questionId, Connection con) {
        ArrayList<Choice> choiceList = new ArrayList<Choice>();

        try {
            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from choices where question_id=?"
            );
            stmt.setInt(1, questionId);

            // Retrieve questions
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Choice curChoice = new Choice(
                        rs.getString("choice_text"),
                        rs.getInt("choice_id"),
                        rs.getBoolean("is_correct")
                );
                choiceList.add(curChoice);
            }

            stmt.close();
        } catch(SQLException e) {

        }

        return choiceList;
    }

    private int getCorrectIndex(ArrayList<Choice> choiceList) {
        for(Choice choice : choiceList) {
            if(choice.isCorrect()) {
                return choice.getId();
            }
        }
        return -1;
    }

    public void addQuiz(Quiz quiz) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into quiz (name, " +
                            "author_id, " +
                            "description, " +
                            "randomize, " +
                            "practice_mode, " +
                            "immediate_correction, " +
                            "display_type, " +
                            "create_time) " +
                            "values (?,?,?,?,?,?,?,?)"
            );

            stmt.setString(1, quiz.getName());
          // to do :  author, stmt.setString(2, );
            stmt.setString(3, quiz.getDescription());
            stmt.setString(4, String.valueOf(quiz.isRandomized()));
            stmt.setString(5, String.valueOf(quiz.isPracticeAllowed()));
            stmt.setString(6, String.valueOf(quiz.isImmediateCorrectionOn()));
            stmt.setString(7, (quiz.getDisplayMode()==1) ? "ONE_PAGE" : "MULTIPLE_PAGES");
          // to do : time,  stmt.setString(8, );


            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}
