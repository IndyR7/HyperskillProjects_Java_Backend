package engine.DTO.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class QuizzesResponse {
    private List<QuizResponse> content;
}
