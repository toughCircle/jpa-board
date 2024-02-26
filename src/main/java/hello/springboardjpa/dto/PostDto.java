package hello.springboardjpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String postContent;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDto> comments;

    public PostDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PostDto(Long id, String title,
                   String name,
                   LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.status = name;
        this.createDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;

    }

    public PostDto(String title, String postContent) {
        this.title = title;
        this.postContent = postContent;
    }

    public PostDto(Long id, String title,
                   String postContent, String name,
                   LocalDateTime createdDate,
                   LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.status = name;
        this.createDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
