package engine.DTO.Responses;

import lombok.Data;

@Data
public class ResultResponse {
    private boolean success;
    private String feedback;

    public ResultResponse(boolean success) {
        this.success = success;
        this.feedback = success ? "Congratulations, you're right!"
                : "Wrong answer! Please, try again.";
    }
}
