package hello.springboardjpa.controller;

import hello.springboardjpa.domain.Comment;
import hello.springboardjpa.dto.CommentDto;
import hello.springboardjpa.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @RequestBody CommentDto requestComment) {
        Comment comment = new Comment();
        comment.setCommentContent(requestComment.getCommentContent());

        Comment savedComment = commentService.createComment(postId, comment);
        CommentDto commentDto = new CommentDto(savedComment.getId(), savedComment.getCommentContent(), savedComment.getCreatedDate(), savedComment.getLastModifiedDate());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    // 수정
    @PostMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDto requestComment) {
        Comment savedComment = commentService.updateComment(commentId, requestComment);
        CommentDto commentDto = new CommentDto(savedComment.getId(), savedComment.getCommentContent(), savedComment.getCreatedDate(), savedComment.getLastModifiedDate());
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        log.info("comment delete");
    }
}
