package planshare.server.planshare.friend.dto;

import lombok.Getter;

@Getter
public class ProfileDTO {

    private String email;
    private String nickName;
    private Boolean status; // true: 팔로우 중인 상태 / false: 팔로우 안한 상태

    public ProfileDTO(String email, String nickName, Boolean status) {
        this.email = email;
        this.nickName = nickName;
        this.status = status;
    }
}
