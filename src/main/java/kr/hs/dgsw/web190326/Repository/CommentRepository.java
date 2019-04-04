package kr.hs.dgsw.web190326.Repository;

import kr.hs.dgsw.web190326.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
