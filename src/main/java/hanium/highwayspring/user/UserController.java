package hanium.highwayspring.user;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/user")
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseDTO<?> join(@RequestBody UserRequestDto userRequestDto) {
        return ResponseDTO.success(userService.register(userRequestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequest) throws Exception {
        return ResponseEntity.ok().body(userService.doLogin(userRequest));
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(userService.issueAccessToken(request));
    }

    @GetMapping("/api/check")
    public ResponseDTO<?> ApiCheck(){
        return ResponseDTO.success(true);
    }
}
