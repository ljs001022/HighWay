package hanium.highwayspring.board;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/board")
@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final SchoolService schoolService;
    private final UserService userService;

    public BoardController(BoardService boardService, SchoolService schoolService, UserService userService) {
        this.boardService = boardService;
        this.schoolService = schoolService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseDTO<?> createBoard(BoardDTO dto, HttpServletRequest request) {
        try {
            User user = userService.getUser(request)
                    .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
            Board entity = BoardDTO.toEntity(dto, user);
            return ResponseDTO.success(boardService.create(entity));
        } catch (Exception e){
            return  ResponseDTO.fail("error", e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseDTO<?> getBoardList() {
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        return ResponseDTO.success(boardService.boardList(school.getId()));
    }

    @PutMapping
    public ResponseDTO<?> updateBoard(BoardDTO dto) {
        return ResponseDTO.success((boardService.update(dto)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBoard(BoardDTO dto) {
        return ResponseEntity.ok().body(boardService.delete(dto.getId()));
    }
}
