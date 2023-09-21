package engine.DTO.Requests;

import lombok.Data;

import java.util.List;

@Data
public class PostAnswerRequest {
    private List<Integer> answer;
}
