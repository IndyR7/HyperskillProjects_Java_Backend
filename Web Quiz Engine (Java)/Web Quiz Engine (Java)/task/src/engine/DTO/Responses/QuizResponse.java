package engine.DTO.Responses;

import engine.Entities.Quiz;
import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {
    private long id;
    private String title;
    private String text;
    private List<String> options;

    public QuizResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions();
    }
}