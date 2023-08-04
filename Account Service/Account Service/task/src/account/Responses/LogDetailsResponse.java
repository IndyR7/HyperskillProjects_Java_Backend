package account.Responses;

import account.Constants.LogType;
import account.Entities.Log;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogDetailsResponse {
    private long id;
    private LocalDateTime date;
    private LogType action;
    private String subject;
    private String object;
    private String path;

    public LogDetailsResponse(Log log) {
        this.id = log.getId();
        this.date = log.getDate();
        this.action = log.getAction();
        this.subject = log.getSubject();
        this.object = log.getObject();
        this.path = log.getPath();
    }
}
