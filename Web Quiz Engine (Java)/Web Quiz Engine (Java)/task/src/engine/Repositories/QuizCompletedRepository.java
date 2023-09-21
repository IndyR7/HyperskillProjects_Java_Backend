package engine.Repositories;

import engine.Entities.QuizCompleted;
import engine.Entities.User;
import engine.DTO.Projections.QuizCompletedProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepository extends JpaRepository<QuizCompleted, Long> {
    @Query("SELECT qc.quiz.id AS id, qc.completedAt AS completedAt " +
            "FROM QuizCompleted qc " +
            "WHERE qc.user = ?1 " +
            "ORDER BY qc.completedAt DESC")
    Page<QuizCompletedProjection> findByUserOrderByCompletedAtDesc(User user, Pageable pageable);
}