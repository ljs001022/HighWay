package hanium.highwayspring.board.repository;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.dto.BoardResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepositoryCustom {
    Slice<BoardResponseDto> getBoardSlice(Pageable pageable);
}
