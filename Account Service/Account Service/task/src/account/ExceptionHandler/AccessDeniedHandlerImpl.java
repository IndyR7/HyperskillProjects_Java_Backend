package account.ExceptionHandler;

import account.Services.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private final LogService logService;

    @Autowired
    public AccessDeniedHandlerImpl(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(), "Access Denied!",
                request.getRequestURI());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        logService.logAccessDenied(request.getRequestURI());

        try (OutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, errorDetails);
        }
    }
}