package hanium.highwayspring.board;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/board")
@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseDTO<?> createBoard(BoardDTO dto, HttpServletRequest request) {
        User user = userService.getUser(request).getData();
        return ResponseDTO.success(boardService.create(dto, user));
    }

    @GetMapping("/myList")
    public ResponseDTO<?> getMyBoardList(HttpServletRequest request){
        return ResponseDTO.success(boardService.myboardList(userService.getUser(request).getData()));
    }

    @GetMapping("/list/{state}")
    public ResponseDTO<?> getBoardList(@PathVariable(name = "state") Long st) {
        return ResponseDTO.success(boardService.boardList(st));
    }

    @PutMapping("/changeState/{boardId}")
    public ResponseDTO<?> changeState(@PathVariable(name = "boardId") Long bid, @RequestParam(name = "state") Long state){
        return ResponseDTO.success(boardService.changeState(bid, state));
    }

    @PutMapping("/update")
    public ResponseDTO<?> updateBoard(BoardDTO dto) {
        return ResponseDTO.success((boardService.update(dto)));
    }

    @PutMapping("/delete")
    public ResponseDTO<?> deleteBoard(BoardDTO dto) {
        return ResponseDTO.success((boardService.delete(dto)));
    }
}
