package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // insert
    public Board create(BoardDTO dto, User user) {
        Board board = Board.builder()
                .content(dto.getContent())
                .state(user.getRole()==1L?1L:0L)
                .user(user)
                .build();
        return boardRepository.save(board);
    }

    public List<Board> boardList(Long state) {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }

    // update
    @Transactional
    public List<Board> update(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.updateBoard(dto.getContent());
        return boardList(1L);
    }

    // delete
    @Transactional
    public List<Board> delete(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.deleteBoard();
        return boardList(1L);
    }

    // 리팩토링하나 메서드
    private void validate(final Board entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("entity cannot be null.");
        }

        if (entity.getUser().getUid() == null) {
            log.warn("Unkown user.");
            throw new RuntimeException("Unknown user");
        }
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }
}
 