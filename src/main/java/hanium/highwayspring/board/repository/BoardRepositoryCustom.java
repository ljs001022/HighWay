package hanium.highwayspring.board.repository;

import com.querydsl.core.Tuple;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.ResponseBoardDTO;
import hanium.highwayspring.board.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    List<Board> findBoardList(Long schId, Long cateNo);
    Optional<ResponseBoardDTO> findBoardDetail(Long uId, Long boardId);
    List<Board> findBoardHeartList(Long uId);
}