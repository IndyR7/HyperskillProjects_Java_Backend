package account.Entities;

import account.Constants.LogType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    LogType action;

    @Column(name = "subject")
    String subject;

    @Column(name = "object")
    String object;

    @Column(name = "path")
    String path;

    public void setValues(LocalDateTime date, LogType action, String subject, String object, String path) {
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }
}
