package hello.springboardjpa.service;

import hello.springboardjpa.domain.Comment;
import hello.springboardjpa.domain.Post;
import hello.springboardjpa.dto.CommentDto;
import hello.springboardjpa.repository.CommentRepository;
import hello.springboardjpa.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 등록
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        // id를 이용하여 Comment 객체 조회
        Optional<Comment> commentOptional = commentRepository.findById(id);

        // Comment 객체가 존재하는 경우 삭제 로직 실행
        commentOptional.ifPresent(comment -> {
                comment.setIsDeleted(true);
                commentRepository.save(comment);
        });
    };

    // 댓글 수정
    public Comment updateComment(Long id, CommentDto updateParam) {
        Comment findComment = commentRepository.findById(id).orElseThrow();
        findComment.setCommentContent(updateParam.getCommentContent());
        return commentRepository.save(findComment);
    }
}
