package hello.springboardjpa;

import hello.springboardjpa.domain.Comment;
import hello.springboardjpa.domain.Post;
import hello.springboardjpa.domain.Status;
import hello.springboardjpa.repository.CommentRepository;
import hello.springboardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        Post post = new Post();
        post.setTitle("A");
        post.setPostContent("a");
        post.setStatus(Status.CREATED);

        postRepository.save(post);

        Comment comment = new Comment();
        comment.setCommentContent("aaaaaa");
        comment.setPost(post);

        commentRepository.save(comment);
    }

}