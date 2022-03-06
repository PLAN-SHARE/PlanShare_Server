package planshare.server.planshare.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String path = ((HttpServletRequest) request).getServletPath();

        if (path.contains("/login")) {
            chain.doFilter(request, response);
            System.out.println("필터 제외된 url!!!!!!!!!");
        } else {
            String token = jwtUtil.resolveToken((HttpServletRequest) request);

            // 유효한 토큰인지 확인
            if (token != null && jwtUtil.validateToken(token)) {
                // 토큰으로부터 유저 정보를 받아옴
                Authentication authentication = jwtUtil.getAuthentication(token);

                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }
    }
}
