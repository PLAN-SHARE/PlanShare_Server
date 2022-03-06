package planshare.server.planshare.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.security.JWTUtil;
import planshare.server.planshare.user.dto.KakaoUserInfo;
import planshare.server.planshare.user.dto.TokenResponse;


@RequiredArgsConstructor
@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @Value("${client.id}")
    private String clientId;

    public String login(String token) {

        HttpHeaders headers = new HttpHeaders();
        //사용자 정보 가져오기
        headers.clear();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoUserInfo> resp = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                KakaoUserInfo.class
        );

        //jwt토큰 생성
        return jwtUtil.createJWT(resp.getBody().getId(), resp.getBody().getKakaoAccount().getEmail());
    }

    // code를 통해 accessToken 받음
    public String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("User-Agent", "api-test");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", "http://localhost:9090/login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();

        // {code} 를 이용해 access_token 요청
        ResponseEntity<TokenResponse> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                TokenResponse.class
        );
        headers.clear();

        return response.getBody().getAccess_token();
    }
}
