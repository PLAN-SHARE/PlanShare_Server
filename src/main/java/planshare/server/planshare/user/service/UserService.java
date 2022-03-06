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
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.security.JWTUtil;
import planshare.server.planshare.user.dto.JWTDTO;
import planshare.server.planshare.user.dto.KakaoUserInfo;
import planshare.server.planshare.user.dto.SignUpDTO;
import planshare.server.planshare.user.dto.TokenResponse;


@RequiredArgsConstructor
@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @Value("${client.id}")
    private String clientId;

    public JWTDTO login(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoUserInfo> resp = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                KakaoUserInfo.class
        );

        // 유저정보가 DB에 없으면 JWT Null 반환
        // 유저정보가 있으면 DB에 넣을필요없이 JWT 새로 발급
        String email = resp.getBody().getKakaoAccount().getEmail();
        Long kakaoId = resp.getBody().getId();

        if (memberRepository.findByEmail(email).isPresent()) {
            String jwt = jwtUtil.createJWT(resp.getBody().getId(), resp.getBody().getKakaoAccount().getEmail());
            return new JWTDTO(kakaoId, email, jwt);
        }

        return new JWTDTO(kakaoId, email, null);
    }

    public String saveMember(SignUpDTO signUpDTO) {
        // DTO를 그대로 파라미터로 넣으면 의존성때문에 밑과 같이 분리해서 넣어주는게 좋습니다.
        Member member = Member.createMember(signUpDTO.getKakaoId(), signUpDTO.getEmail(), signUpDTO.getNickName());
        memberRepository.save(member);
        return jwtUtil.createJWT(signUpDTO.getKakaoId(), signUpDTO.getEmail());
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
