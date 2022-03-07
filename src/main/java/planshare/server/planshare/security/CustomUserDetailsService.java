package planshare.server.planshare.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.")); // 에러를 처리하는 것이 중요하다.


        return CustomUserDetailsVO.toVO(member);
    }
}
