package hello.springboardjpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    private String title;

//    @Lob
    private String postContent;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean isDeleted = false;

}
