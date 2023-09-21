package engine.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "quizzes_completed")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "quiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "completed_at")
    @JsonProperty(value = "completedAt")
    private LocalDateTime completedAt;

    public void setValues(Quiz quiz, User user) {
        this.user = user;
        this.quiz = quiz;
        this.completedAt = LocalDateTime.now();
    }
}