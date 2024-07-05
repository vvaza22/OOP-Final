package Question;

import java.util.ArrayList;

public class PictureResponse extends TextQuestion {

    /* Web link to the picture */
    private final String pictureLink;

    public PictureResponse(String questionText, String pictureLink, int questionId, ArrayList<String> correctAnswerList) {
        super(questionText, QuestionType.PICTURE_RESPONSE, questionId, correctAnswerList);
        this.pictureLink = pictureLink;
    }

    public String getPicture() {
        return pictureLink;
    }
}
