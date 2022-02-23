package planshare.server.planshare.user.domain;

import lombok.Getter;

@Getter
public class TokenResponse {
    String token_type;
    String access_token;
    String expires_in;
    String refresh_token;
    String refresh_token_expires_in;
}
