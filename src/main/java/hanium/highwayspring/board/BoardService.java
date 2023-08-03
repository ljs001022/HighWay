package hanium.highwayspring.board;

import java.util.List;

import hanium.highwayspring.board.dto.BoardRequestDTO;
import hanium.highwayspring.board.dto.BoardResponseDto;
import hanium.highwayspring.board.repository.BoardRepository;
import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // insert
    public Board create(BoardRequestDTO dto, User user) {
        Board board = Board.builder()
                .content(dto.getContent())
                .state(user.getRole()==1L?1L:0L)
                .user(user)
                .build();
        return boardRepository.save(board);
    }

    public List<Board> myboardList(User user){
        return boardRepository.findByUserId(user.getId());
    }

    public List<Board> boardList(Long state) {
        return boardRepository.findByState(state);
    }

    public Slice<BoardResponseDto> boardPage(Pageable pageable){
        return boardRepository.getBoardSlice(pageable);
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
    public List<Board> update(BoardRequestDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.updateBoard(dto.getContent());
        return boardList(dto.getState());
    }

    // delete
    @Transactional
    public List<Board> delete(BoardRequestDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board.deleteBoard();
        return boardList(dto.getState());
    }
}
 