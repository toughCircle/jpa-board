package hello.springboardjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String commentContent;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    public CommentDto(String commentContent) {
        this.commentContent = commentContent;
    }
}
