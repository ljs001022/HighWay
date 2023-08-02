package hanium.highwayspring.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardResponseDto {
    //게시판
    private Long id;
    private String content;
    private Long isDeleted;
    private Long state;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    //유저정보
    private Long userId;
    private String userName;
    @QueryProjection
    public BoardResponseDto(Board board, User user){
        this.id = board.getId();
        this.content = board.getContent();
        this.isDeleted = board.getIsDeleted();
        this.state = board.getState();
        this.createDate = board.getCreateDate();
        this.modifiedDate = board.getModifiedDate();
        this.userId = user.getId();
        this.userName = user.getName();
    }
}
