package engine.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "quizzes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "options")
    @ElementCollection
    private List<String> options;

    @Column(name = "answer")
    @ElementCollection
    private List<Integer> answers;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    List<QuizCompleted> quizzesCompleted;

    public void setValues(User user, String title, String text, List<String> options, List<Integer> answers) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answers = answers;
    }
}