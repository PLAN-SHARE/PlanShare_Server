package planshare.server.planshare.user.dto;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import planshare.server.planshare.domain.Member;

import java.util.Collection;

public class CustomUserDetailsVO implements UserDetails {

    private Long id;
    private Long kakaoId;
    private String email;

    public static CustomUserDetailsVO toVO(Member member) {
        CustomUserDetailsVO customUserDetailsVO = new CustomUserDetailsVO();
        customUserDetailsVO.email = member.getEmail();
        customUserDetailsVO.kakaoId = null; // 이후에 카카오 ID를 디비에 넣는다면 저장해야한다.
        customUserDetailsVO.id = member.getId();

        return customUserDetailsVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
