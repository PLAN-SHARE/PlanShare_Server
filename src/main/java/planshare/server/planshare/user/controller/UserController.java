package planshare.server.planshare.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import planshare.server.planshare.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // accessToken -> user 정보 받아서 jwt토큰 생성 후 반환
    @GetMapping("/user/login")
    public String login(@RequestParam String accessToken){
        return userService.login(accessToken);
    }

    // code -> accessToken 반환
    @GetMapping("/test/code")
    public String getAccessToken(@RequestParam String code) {
        String jwtToken = userService.getAccessToken(code);
        return jwtToken;
    }

    @GetMapping("/checkJWT")
    public String list() {
        //권한체크
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        return (String) user.getPrincipal();
    }
}
