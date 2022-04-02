package planshare.server.planshare.friend.dto;

import lombok.Getter;
import planshare.server.planshare.domain.Member;

@Getter
public class ProfileDTO {

    private Member member;
    private Boolean status; // true: 팔로우 중인 상태 / false: 팔로우 안한 상태

    public ProfileDTO(Member member, Boolean status) {
        this.member = member;
        this.status = status;
    }
}
