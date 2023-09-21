package engine.Controllers;

import engine.DTO.Projections.QuizCompletedProjection;
import engine.DTO.Requests.PostAnswerRequest;
import engine.DTO.Requests.PostQuizRequest;
import engine.DTO.Responses.QuizResponse;
import engine.DTO.Responses.ResultResponse;
import engine.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<Page<QuizResponse>> getQuizzes(@RequestParam(defaultValue = "0") int page) {
        return quizService.getQuizzes(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping
    public ResponseEntity<QuizResponse> postQuiz(@Valid @RequestBody PostQuizRequest request) {
        return quizService.postQuiz(request);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<ResultResponse> postResult(@PathVariable long id, @RequestBody PostAnswerRequest answer) {
        return quizService.postResult(id, answer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable long id) {
        return quizService.deleteQuiz(id);
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<QuizCompletedProjection>> getQuizzesCompleted(@RequestParam(defaultValue = "0") int page) {
        return quizService.getQuizzesCompleted(page);
    }
}