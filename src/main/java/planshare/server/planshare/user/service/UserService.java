package planshare.server.planshare.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import planshare.server.planshare.user.dto.KakaoUserInfo;
import planshare.server.planshare.util.JWTUtil;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JWTUtil jwtUtil;

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
        return jwtUtil.makeJWT(resp.getBody().getId(), resp.getBody().getKakaoAccount().getEmail());
    }
}
