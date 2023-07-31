package hanium.highwayspring.board.repository;

import java.util.List;

import hanium.highwayspring.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    List<Board> findByState(Long state);

    List<Board> findByUserId(Long userId);
}
