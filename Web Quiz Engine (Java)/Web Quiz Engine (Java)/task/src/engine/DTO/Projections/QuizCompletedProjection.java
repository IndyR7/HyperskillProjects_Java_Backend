package engine.DTO.Projections;

import java.time.LocalDateTime;

public interface QuizCompletedProjection {
    Long getId();
    LocalDateTime getCompletedAt();
}
