package planshare.server.planshare.friend.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.friend.dto.ProfileDTO;
import planshare.server.planshare.friend.service.FriendService;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    //유저 컨트롤러에서 추가할 거: 클라이언트에서 닉넴(or email) 검색 시 해당 유저 닉넴(or email) 반환 기능 -> 친구추가 리퀘스트 보낼 수 있도록
    @ApiOperation(value = "email로 친구 검색 API", notes = "email -> 해당 유저 닉넴 & email 반환 (프로필)")
    @GetMapping ("/friend/search")
    public ProfileDTO search(@RequestParam String email, @AuthenticationPrincipal CustomUserDetailsVO userDetails) {
        System.out.println(userDetails);
        return friendService.search(email, userDetails);
    }

    @ApiOperation(value = "친구 follow API", notes = "친구 email -> 해당 친구 follow 함")
    @PostMapping("/friend/follow")
    public ProfileDTO follow(@RequestParam String toMemberEmail, @AuthenticationPrincipal CustomUserDetailsVO userDetails) {
        return friendService.follow(toMemberEmail, userDetails);
    }

    @ApiOperation(value = "친구 unfollow API", notes = "친구 email -> 해당 친구 unfollow 함")
    @PostMapping("/friend/unfollow")
    public ProfileDTO unfollow(@RequestParam String toMemberEmail, @AuthenticationPrincipal CustomUserDetailsVO userDetails) {
        return friendService.unfollow(toMemberEmail, userDetails);
    }

    @ApiOperation(value = "follow 목록 조회 API", notes = "follow 리스트 반화")
    @GetMapping("/friend/follow/list")
    public List<Member> followList(@AuthenticationPrincipal CustomUserDetailsVO userDetails) {
        return friendService.findFollow(userDetails);
    }

    @ApiOperation(value = "following 목록 조회 API", notes = "following 리스트 반환")
    @GetMapping("/friend/following/list")
    public List<Member> followingList(@AuthenticationPrincipal CustomUserDetailsVO userDetails) {
        return friendService.findFollowing(userDetails);
    }
}
