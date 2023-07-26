package hanium.highwayspring.board;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.comment.Comment;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "board_TB")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //이 오브젝트의 아이디
    private String content;

    //삭제여부 0 : 삭제안됨 1 : 삭제됨
    @Column(columnDefinition = "integer default 0")
    private Long isDeleted;
    //0:승인대기 1:승인완료 2:승인반려
    @Column(columnDefinition = "integer default 0")
    private Long state;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createDate;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime modifiedDate;

    public void changeState(Long state){
        this.state = state;
    }

    public void deleteBoard(){
        this.isDeleted = 1L;
    }

    public void updateBoard(String content){
        this.content = content;
    }
}
