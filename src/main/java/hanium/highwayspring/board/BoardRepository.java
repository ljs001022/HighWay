package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findByUserId(String userId);
	List<Board> findBySchoolId(Long schoolId);
	Optional<Board> findById(Long id);
}
