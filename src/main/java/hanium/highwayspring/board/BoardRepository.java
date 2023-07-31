package hanium.highwayspring.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{
    List<Board> findByState(Long state);
    List<Board> findByUserId(Long userId);
}
