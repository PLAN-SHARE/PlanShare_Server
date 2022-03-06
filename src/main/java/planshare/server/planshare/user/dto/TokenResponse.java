package planshare.server.planshare.user.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    String access_token;
    String scope;
    String token_type;
}
