package planshare.server.planshare.user.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    // 무조건 DTO나 도메인은 private형으로 선언해야함
    private String access_token;
    private String scope;
    private String token_type;
}
