package planshare.server.planshare.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;
import planshare.server.planshare.user.dto.JWTDTO;
import planshare.server.planshare.user.dto.SignUpDTO;
import planshare.server.planshare.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @ApiOperation(value = "카카오 로그인", notes = "accessToken -> user 정보 받아서 jwt토큰 생성 후 반환 없으면 Null")
    @GetMapping("/user/login")
    public JWTDTO login(@RequestParam String accessToken) {
        return userService.login(accessToken);
    }

    @ApiOperation(value = "인가코드를 넣으면 accessToken을 반환하는 API", notes = "인가코드를 넣으면 accessToken 반환")
    @GetMapping("/test/code")
    public String getAccessToken(@RequestParam String code) {
        return userService.getAccessToken(code);
    }

    @GetMapping("/checkJWT")
    public UserDetails list(@AuthenticationPrincipal CustomUserDetailsVO cud) {
        //현재 로그인 되어있는 사람이 넘겨준 JWT에 어떠한 내용이 담겨져있는지 확인하는 컨트롤러
/*        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        return userDetails;*/
        return cud;
    }

    @ApiOperation(value = "회원가입 API", notes = "닉네임,카카오ID,email을 주면 회원가입")
    @PostMapping("/user/signup")
    public String singUp(@RequestBody SignUpDTO signUpDto) {
        return userService.saveMember(signUpDto);
    }
}
