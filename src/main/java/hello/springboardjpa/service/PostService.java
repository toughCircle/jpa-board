package hello.springboardjpa.service;

import hello.springboardjpa.domain.Comment;
import hello.springboardjpa.domain.Post;
import hello.springboardjpa.dto.CommentDto;
import hello.springboardjpa.dto.PostDto;
import hello.springboardjpa.domain.Status;
import hello.springboardjpa.repository.CommentRepository;
import hello.springboardjpa.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // 저장
    public Post createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setPostContent(postDto.getPostContent());
        post.setStatus(Status.CREATED);
        post.setIsDeleted(false);
        return postRepository.save(post);
    }


    // 게시글 삭제시 댓글 함께 삭제
    public void deletePost(Long id) {
        // id를 이용하여 Post 객체 조회
        Optional<Post> postOptional = postRepository.findById(id);

        // Post 객체가 존재하는 경우 삭제 로직 실행
        postOptional.ifPresent(post -> {
            post.setIsDeleted(true);
            postRepository.save(post);

            for (Comment comment : post.getComments()) {
                comment.setIsDeleted(true);
                commentRepository.save(comment);
            }
        });

        // 예외 처리: Post 객체가 존재하지 않는 경우, 예외를 발생시킬 수 있음
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found with id " + id);
        }
    }

    // 수정
    public void updatePost(Long id, PostDto updateParam) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        findPost.setTitle(updateParam.getTitle());
        findPost.setPostContent(updateParam.getPostContent());
        findPost.setStatus(Status.UPDATED);
        postRepository.save(findPost); // 이 시점에 lastModifiedDate 자동 업데이트
    }

    // 게시글 목록 조회 (응답에 본문내용 제외)
    public Page<PostDto> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findByIsDeletedFalse(pageable);
        return postPage.map(post -> new PostDto(post.getId(), post.getTitle(), post.getStatus().name(),
            post.getCreatedDate(), post.getLastModifiedDate()));
    }

    // 게시글 단건 조회 (제목, 본문, 게시글에 등록된 댓글 포함)
    // 응답 내용 호출
    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        List<CommentDto> commentDtos = commentRepository.findByPostIdAndIsDeletedFalse(postId)
            .stream()
            .map(comment -> new CommentDto(comment.getId(), comment.getCommentContent(), comment.getCreatedDate(), comment.getLastModifiedDate()))
            .collect(Collectors.toList());

        return new PostDto(
            post.getId(),
            post.getTitle(),
            post.getPostContent(),
            post.getStatus().name(),
            post.getCreatedDate(),
            post.getLastModifiedDate(),
            commentDtos
        );
    }

}

