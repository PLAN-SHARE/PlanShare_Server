package planshare.server.planshare.user.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import planshare.server.planshare.user.domain.TokenResponse;

@RestController
public class UserController {

    // code 발급
    @RequestMapping("/login")
    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("code:" + code);
        return "code test";
    }

    // code로 토큰 발급
    @GetMapping("/login/token")
    public TokenResponse getToken(@RequestParam String code) {
        System.out.println(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "?");
        params.add("redirect_uri", "http://localhost:9090/login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<TokenResponse> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                TokenResponse.class
        );

        return response.getBody();
    }

}
