package hanium.highwayspring.board;

import java.util.List;

import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
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
        return boardRepository.findByState(state);
    }

    public List<Board> myboardList(User user){
        return boardRepository.findByUserId(user.getId());
    }

    // accept
    @Transactional
    public List<Board> changeState(Long boardId, Long state) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.changeState(state);
        return boardList(0L);
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

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }
}
 