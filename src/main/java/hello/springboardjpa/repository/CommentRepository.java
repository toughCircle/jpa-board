package hello.springboardjpa.repository;

import hello.springboardjpa.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // id 기반 댓글 조회 메소드
    List<Comment> findByPostIdAndIsDeletedFalse(Long postId);
}
