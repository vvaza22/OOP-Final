package Quiz;

import Account.Account;
import Database.Database;
import Question.*;
import org.json.JSONObject;

import javax.jms.ConnectionConsumer;
import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class QuizManager {
    private final Database db;

    public QuizManager(Database db) {
        this.db = db;
    }

    public ArrayList<Quiz> getPopularQuizzes() {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select q.quiz_id AS ID, " +
                            "count(a.attempt_id) AS total_attempts " +
                            "from quiz q " +
                            "left outer join attempts a " +
                            "on(q.quiz_id = a.quiz_id) " +
                            "where q.is_deleted=0 " +
                            "group by ID " +
                            "order by total_attempts desc limit 5;"
            );

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                list.add(getQuiz(id));
            }

            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<ScoresStruct> getRecentQuizTakers(int quizId) {
        ArrayList<ScoresStruct> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the scorers
            PreparedStatement stmt = con.prepareStatement(
                    "select u.user_name, a.score " +
                            "from attempts a " +
                            "inner join users u on a.user_id = u.id " +
                            "where quiz_id=? " +
                            "and is_deleted=0 " +
                            "order by a.attempt_time desc;"
            );

            stmt.setInt(1, quizId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String userName =  rs.getString("user_name");
                int score = rs.getInt("score");
                list.add(new ScoresStruct(userName, score));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Quiz> getFriendCreatedQuizzes(int userId) {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select quiz_id " +
                            "from quiz " +
                            "where author_id in (select friend_B " +
                                            "from friends " +
                                            "where friend_A=?) " +
                            "and is_deleted=0 " +
                            "limit 10;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("quiz_id");
                list.add(getQuiz(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Quiz> getFriendTakenQuizzes(int userId) {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select quiz_id " +
                            "from attempts " +
                            "where user_id in (select friend_B " +
                            "                    from friends " +
                            "                    where friend_A=?) " +
                            "order by attempt_time desc limit 10;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("quiz_id");
                list.add(getQuiz(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public ArrayList<ScoresStruct> getTodaysTopScorers(int quizId) {
        ArrayList<ScoresStruct> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the scorers
            PreparedStatement stmt = con.prepareStatement(
                    "select u.user_name, " +
                            "    (select max(a.score) " +
                            "         from attempts a " +
                            "         where a.user_id = u.id " +
                            "         and a.quiz_id = ? " +
                            "         and date(a.attempt_time) = curdate() " +
                            "    ) as max_score " +
                            "from users u " +
                            "where u.is_deleted=0 " +
                            "having max_score is not null " +
                            "order by 2 desc limit 5;"
            );

            stmt.setInt(1, quizId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String userName =  rs.getString("user_name");
                int score = rs.getInt("max_score");
                list.add(new ScoresStruct(userName, score));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public Integer getAverageScore(int quizId) {
        Integer score = null;
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the scorers
            PreparedStatement stmt = con.prepareStatement(
                    "select " +
                            "    avg((select max(a.score) " +
                            "         from attempts a " +
                            "         where a.user_id = u.id " +
                            "         and quiz_id =? " +
                            "    )) as avg_score " +
                            "from users u " +
                            "where u.is_deleted=0;"
            );

            stmt.setInt(1, quizId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                score = rs.getInt("avg_score");

            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    public ArrayList<ScoresStruct> getTopScorers(int quizId) {
        ArrayList<ScoresStruct> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the scorers
            PreparedStatement stmt = con.prepareStatement(
                    "select u.user_name, " +
                            "(select max(a.score) " +
                            "   from attempts a " +
                            "   where a.user_id = u.id " +
                            "   and quiz_id=?" +
                            ") as max_score " +
                            "from users u " +
                            "where is_deleted=0 " +
                            "having max_score is not null " +
                            "order by 2 desc limit 5;"
            );

            stmt.setInt(1, quizId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String userName =  rs.getString("user_name");
                int score = rs.getInt("max_score");
                list.add(new ScoresStruct(userName, score));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public ArrayList<Quiz> getRecentQuizzes() {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select quiz_id AS ID, create_time " +
                        "from quiz " +
                        "where is_deleted=0 " +
                        "order by 2 desc;"
            );

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                list.add(getQuiz(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public ArrayList<Quiz> getRecentlyTakenQuizzes(int userId) {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select a.quiz_id AS ID, max(a.attempt_time) AS latest_attempt_time " +
                        "from attempts a " +
                        "where a.user_id=? " +
                        "group by a.quiz_id " +
                        "order by latest_attempt_time desc;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                list.add(getQuiz(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Quiz> getRecentlyCreatedQuizzes(int userId) {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select quiz_id AS ID, create_time " +
                        "from quiz " +
                        "where author_id=? " +
                        "order by 2 desc;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                list.add(getQuiz(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void deleteQuiz(int quizId) {
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Delete the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "update quiz set is_deleted=1 where quiz_id=?;"
            );

            stmt.setInt(1, quizId);
            stmt.executeUpdate();

            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Quiz getRandomQuiz() {
        try {
            // Open connection to the database
            Connection con = db.openConnection();
            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from quiz where is_deleted=0 order by rand() LIMIT 1;"
            );
            // Create Quiz Object
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int quizId = rs.getInt("quiz_id");
                Quiz quiz = new Quiz(
                        quizId,
                        rs.getString("name"),
                        rs.getInt("author_id"),
                        rs.getString("description"),
                        rs.getString("quiz_image"),
                        rs.getBoolean("randomize"),
                        rs.getBoolean("practice_mode"),
                        rs.getBoolean("immediate_correction"),
                        rs.getString("display_type").equals("ONE_PAGE") ? Quiz.ONE_PAGE : Quiz.MULTIPLE_PAGES,
                        (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("create_time"))),
                        getQuestions(quizId, con)
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

    public Quiz getQuiz(int id) {
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from quiz where quiz_id=? and is_deleted=0;"
            );
            stmt.setInt(1, id);

            // Create Quiz Object
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Quiz quiz = new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("name"),
                        rs.getInt("author_id"),
                        rs.getString("description"),
                        rs.getString("quiz_image"),
                        rs.getBoolean("randomize"),
                        rs.getBoolean("practice_mode"),
                        rs.getBoolean("immediate_correction"),
                        rs.getString("display_type").equals("ONE_PAGE") ? Quiz.ONE_PAGE : Quiz.MULTIPLE_PAGES,
                        (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("create_time"))),
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
                                rs.getInt("question_id"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                        questionList.add(questionResponse);
                        break;
                    case QuestionType.FILL_BLANK:
                        FillBlank fillBlank = new FillBlank(
                                rs.getString("question_text"),
                                rs.getInt("question_id"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                        questionList.add(fillBlank);
                        break;
                    case QuestionType.MULTIPLE_CHOICE:
                        ArrayList<Choice> choiceList = getChoiceList(rs.getInt("question_id"), con);
                        MultipleChoice multipleChoice = new MultipleChoice(
                                rs.getString("question_text"),
                                rs.getInt("question_id"),
                                choiceList,
                                getCorrectIndex(choiceList)
                        );
                        questionList.add(multipleChoice);
                        break;
                    case QuestionType.PICTURE_RESPONSE:
                        PictureResponse pictureResponse = new PictureResponse(
                                rs.getString("question_text"),
                                rs.getString("picture"),
                                rs.getInt("question_id"),
                                getTextAnswerList(rs.getInt("question_id"), con)
                        );
                        questionList.add(pictureResponse);
                        break;
                }
            }

            stmt.close();

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

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
            throw new RuntimeException(e);
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
                        rs.getInt("is_correct") == 1
                );
                choiceList.add(curChoice);
            }

            stmt.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return choiceList;
    }

    public long saveAttempt(int userId, Quiz quiz) {
        int maxScore = quiz.getMaxScore();
        int userScore = quiz.countScore();

        long attemptId = -1;

        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "insert into attempts(quiz_id, user_id, max_possible, score) values(?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, quiz.getId());
            stmt.setInt(2, userId);
            stmt.setInt(3, maxScore);
            stmt.setInt(4, userScore);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    attemptId = rs.getLong(1);
                    saveUserAnswers(attemptId, quiz, con);
                }
            }

            stmt.close();
            con.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return attemptId;
    }

    private void saveUserAnswers(long attemptId, Quiz quiz, Connection con) {
        ArrayList<Question> questionList = quiz.getQuestions();

        for(Question question : questionList) {
            saveUserAnswer(attemptId, question, con);
        }

    }

    private void saveUserAnswer(long attemptId, Question question, Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "insert into user_answers(attempt_id, question, user_answer, correct_answer, points)" +
                            " values(?, ?, ?, ?, ?)"
            );
            stmt.setLong(1, attemptId);
            stmt.setString(2, question.getQuestionText());
            stmt.setString(3, getUserAnswerFromQuestion(question));
            stmt.setString(4, getCorrectAnswerFromQuestion(question));
            stmt.setInt(5, question.countPoints());

            stmt.executeUpdate();

            stmt.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserAnswerFromQuestion(Question question) {
        switch (question.getType()) {
            case QuestionType.QUESTION_RESPONSE:
            case QuestionType.FILL_BLANK:
            case QuestionType.PICTURE_RESPONSE:
                TextQuestion textQuestion = (TextQuestion) question;
                String userAnswer = textQuestion.getUserAnswer();
                if(userAnswer == null || userAnswer.isEmpty()) {
                    userAnswer = "*Not answered";
                }
                return userAnswer;
            case QuestionType.MULTIPLE_CHOICE:
                MultipleChoice multipleChoice = (MultipleChoice) question;
                Choice userChoice = multipleChoice.getUserChoice();
                if(userChoice == null) {
                    return "*Not answered";
                }
                return userChoice.getText();
        }
        return "Unknown Question Type";
    }

    private String getCorrectAnswerFromQuestion(Question question) {
        switch (question.getType()) {
            case QuestionType.QUESTION_RESPONSE:
            case QuestionType.FILL_BLANK:
            case QuestionType.PICTURE_RESPONSE:
                TextQuestion textQuestion = (TextQuestion) question;
                ArrayList<String> correctAnswers = textQuestion.getCorrectAnswers();
                return String.join(", ", correctAnswers);
            case QuestionType.MULTIPLE_CHOICE:
                MultipleChoice multipleChoice = (MultipleChoice) question;
                Choice correctChoice = multipleChoice.getCorrectChoice();
                return correctChoice.getText();
        }
        return "Unknown Question Type";
    }

    public ArrayList<UserAnswer> getUserAnswers(int attemptId, Connection con) {
        ArrayList<UserAnswer> userAnswers = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "select * from user_answers where attempt_id=?"
            );
            stmt.setInt(1, attemptId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                UserAnswer userAnswer = new UserAnswer(
                    attemptId,
                    rs.getString("question"),
                    rs.getString("user_answer"),
                    rs.getString("correct_answer"),
                    rs.getInt("points")
                );
                userAnswers.add(userAnswer);
            }

            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userAnswers;
    }

    public Attempt getAttempt(int attemptId) {
        try {
            Connection con = db.openConnection();

            PreparedStatement stmt = con.prepareStatement(
                    "select *, date_format(attempt_time, '%M %e, %Y %H:%i') as finish_time from attempts where attempt_id=?"
            );
            stmt.setInt(1, attemptId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Attempt attempt = new Attempt(
                        rs.getInt("attempt_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("user_id"),
                        rs.getInt("max_possible"),
                        rs.getInt("score"),
                        rs.getString("finish_time"),
                        getUserAnswers(attemptId, con)
                );

                stmt.close();
                con.close();

                return attempt;
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Attempt object does not exist
        return null;
    }

    public ArrayList<Attempt> getAttemptList(int userId, int quizId) {
        ArrayList<Attempt> attemptList = new ArrayList<Attempt>();
        try {
            Connection con = db.openConnection();

            PreparedStatement stmt = con.prepareStatement(
                    "select *, date_format(attempt_time, '%M %e, %Y %H:%i') as finish_time from attempts where user_id=? and quiz_id=? order by attempt_time desc"
            );
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Attempt attempt = new Attempt(
                        rs.getInt("attempt_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("user_id"),
                        rs.getInt("max_possible"),
                        rs.getInt("score"),
                        rs.getString("finish_time"),
                        getUserAnswers(rs.getInt("attempt_id"), con)
                );
                attemptList.add(attempt);
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attemptList;
    }

    private int getCorrectIndex(ArrayList<Choice> choiceList) {
        for(Choice choice : choiceList) {
            if(choice.isCorrect()) {
                return choice.getId();
            }
        }
        return -1;
    }

    public int addQuiz(Quiz quiz) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into quiz (" +
                            "name, " +
                            "author_id, " +
                            "description, " +
                            "quiz_image, " +
                            "randomize, " +
                            "practice_mode, " +
                            "immediate_correction, " +
                            "display_type, " +
                            "create_time) " +
                            "values (?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, quiz.getName());
            stmt.setString(2, String.valueOf(quiz.getAuthorId()));
            stmt.setString(3, quiz.getDescription());
            stmt.setString(4, quiz.getImage());
            stmt.setBoolean(5, quiz.isRandomized());
            stmt.setBoolean(6, quiz.isPracticeAllowed());
            stmt.setBoolean(7, quiz.isImmediateCorrectionOn());
            stmt.setString(8, (quiz.getDisplayMode()==1) ? "ONE_PAGE" : "MULTIPLE_PAGES");
            stmt.setString(9, quiz.getCreateTime());

            stmt.executeUpdate();
            int quizNewId = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                quizNewId = rs.getInt(1);
            }
            stmt.close();

            ArrayList<Question> questions = quiz.getQuestions();
            for(int i=0; i<questions.size(); i++) {
                Question quest = questions.get(i);
                int questNewId = addQuestion(con, quizNewId, quest, i+1);
                addAnswers(con, quest, questNewId);
            }
            con.close();
            return quizNewId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAnswers(Connection con, Question quest, int questId){
        try {
            switch(quest.getType()) {
                case QuestionType.MULTIPLE_CHOICE:
                    ArrayList<Choice> choices = ((MultipleChoice)quest).getChoices();
                    for(int i=0; i<choices.size(); i++) {
                        Choice choice = choices.get(i);
                        String choiceText = choice.getText();
                        int isCorrectInt = 0;
                        if (choice.isCorrect()) {
                            isCorrectInt = 1;
                        }
                        PreparedStatement stmt = con.prepareStatement(
                                "insert into choices (question_id, " +
                                        "choice_text, " +
                                        "is_correct) " +
                                        "values (?,?,?)"
                        );


                        stmt.setString(1, String.valueOf(questId));
                        stmt.setString(2, choiceText);
                        stmt.setString(3, String.valueOf(isCorrectInt));

                        stmt.executeUpdate();
                        stmt.close();
                    }
                    break;
                default:
                    ArrayList<String> answers = ((TextQuestion)quest).getCorrectAnswers();
                    for(int i=0; i<answers.size(); i++) {
                        String answer = answers.get(i);
                        PreparedStatement stmt = con.prepareStatement(
                                "insert into text_answers (question_id, " +
                                        "answer_value) " +
                                        "values (?,?)"
                        );

                        stmt.setString(1, String.valueOf(questId));
                        stmt.setString(2, answer);

                        stmt.executeUpdate();
                        stmt.close();
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int addQuestion(Connection con, int quizId, Question quest, int questOrder) {
        try {
            PreparedStatement stmt =  con.prepareStatement(
                    "insert into questions (quiz_id, " +
                            "question_text, " +
                            "question_type, " +
                            "picture, " +
                            "question_order) " +
                            "values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, String.valueOf(quizId));
            stmt.setString(2, quest.getQuestionText());
            stmt.setString(3, String.valueOf(quest.getType()));
            if(quest.getType() == QuestionType.PICTURE_RESPONSE) {
                stmt.setString(4, ((PictureResponse)quest).getPicture());
            } else {
                stmt.setString(4, null);
            }
            stmt.setString(5, String.valueOf(questOrder));

            stmt.executeUpdate();
            int questNewId = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                questNewId = rs.getInt(1);
            }
            stmt.close();
            return questNewId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQuizCount(int authorId){
        int count = 0;
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from quiz where author_id = ?"
            );
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                count++;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public int getDoneQuizzes(int userId){
        int count = 0;
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(quiz_id) as cnt from " +
                    "(select quiz_id from attempts where user_id=? group by quiz_id) q"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                count = rs.getInt("cnt");
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return count;
    }
}
