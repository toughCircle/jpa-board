package hello.springboardjpa.controller;

import hello.springboardjpa.domain.Post;
import hello.springboardjpa.dto.PostDto;
import hello.springboardjpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 등록
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto requestPost) {
        Post post = new Post();
        post.setTitle(requestPost.getTitle());
        post.setPostContent(requestPost.getPostContent());

        Post savedPost = postService.createPost(requestPost);
        PostDto postDto = new PostDto(savedPost.getId(), savedPost.getTitle(), savedPost.getPostContent(), savedPost.getStatus().toString(), savedPost.getCreatedDate(), savedPost.getLastModifiedDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);

    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        PostDto postDto = postService.getPost(id);
        return ResponseEntity.ok(postDto);
    }

    // 목록 조회
    @GetMapping
    public Page<PostDto> getPost(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        System.out.println("pageable = " + pageable);
        return postService.getAllPosts(pageable);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
//        return ResponseEntity.noContent().build();
    }

    // 수정
    @PostMapping("{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Long id, @RequestBody PostDto requestPost) {
        postService.updatePost(id, requestPost);
        PostDto post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }


}
