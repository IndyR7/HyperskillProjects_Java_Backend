package engine.Services;

import engine.Entities.Quiz;
import engine.Entities.QuizCompleted;
import engine.DTO.Projections.QuizCompletedProjection;
import engine.Repositories.QuizCompletedRepository;
import engine.Repositories.QuizRepository;
import engine.DTO.Requests.PostAnswerRequest;
import engine.DTO.Requests.PostQuizRequest;
import engine.DTO.Responses.QuizResponse;
import engine.DTO.Responses.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizCompletedRepository quizCompletedRepository;
    private final UserService userService;

    @Autowired
    public QuizService(QuizRepository quizRepository,
                       QuizCompletedRepository quizCompletedRepository,
                       UserService userService) {
        this.quizRepository = quizRepository;
        this.userService = userService;
        this.quizCompletedRepository = quizCompletedRepository;
    }

    public ResponseEntity<Page<QuizResponse>> getQuizzes(int page) {
        Page<QuizResponse> quizzes = quizRepository.findAll(PageRequest.of(page, 10))
                .map(QuizResponse::new);

        return ResponseEntity.ok(quizzes);
    }

    public ResponseEntity<QuizResponse> getQuiz(long id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new QuizResponse(quiz));
    }

    public ResponseEntity<QuizResponse> postQuiz(PostQuizRequest request) {
        Quiz quiz = new Quiz();

        quiz.setValues(userService.getCurrentAuthenticatedUser(), request.getTitle(), request.getText(),
                request.getOptions(), request.getAnswer());

        quizRepository.save(quiz);

        return ResponseEntity.ok(new QuizResponse(quiz));
    }

    public ResponseEntity<ResultResponse> postResult(long id, PostAnswerRequest request) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean correct = new HashSet<>(quiz.getAnswers()).containsAll(request.getAnswer())
                && new HashSet<>(request.getAnswer()).containsAll(quiz.getAnswers());


        if (correct) {
            QuizCompleted quizCompleted = new QuizCompleted();

            quizCompleted.setValues(quiz, userService.getCurrentAuthenticatedUser());

            quizCompletedRepository.save(quizCompleted);
        }

        return ResponseEntity.ok(new ResultResponse(correct));
    }

    public ResponseEntity<HttpStatus> deleteQuiz(long id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!quiz.getUser().equals(userService.getCurrentAuthenticatedUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        quizRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Page<QuizCompletedProjection>> getQuizzesCompleted(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<QuizCompletedProjection> quizzesCompleted = quizCompletedRepository.findByUserOrderByCompletedAtDesc(
                userService.getCurrentAuthenticatedUser(), pageable);

        return ResponseEntity.ok(quizzesCompleted);
    }
}