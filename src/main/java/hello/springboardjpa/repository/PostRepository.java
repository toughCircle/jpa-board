package hello.springboardjpa.repository;

import hello.springboardjpa.domain.Post;
import hello.springboardjpa.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 조회 메소드
//    List<Post> findByIsDeletedFalse();
    Page<Post> findByIsDeletedFalse(Pageable pageable);

}

