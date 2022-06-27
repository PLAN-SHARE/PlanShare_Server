package planshare.server.planshare.user.dto;


import lombok.Getter;

@Getter
public class JWTDTO {

    // private final 형은 붙이는게 좋습니다.
    private final Long kakaoId;
    private final String email;
    private final String JWT;
    private final Long userId;

    public JWTDTO(Long kakaoId, String email, String JWT,Long userId) {
        this.JWT = JWT;
        this.kakaoId = kakaoId;
        this.email = email;
        this.userId = userId;
    }
}
