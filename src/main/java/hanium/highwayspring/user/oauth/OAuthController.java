package hanium.highwayspring.user.oauth;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OAuthController {
    private final UserService userService;
    public OAuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code){
        System.out.println(code);
    }
}
