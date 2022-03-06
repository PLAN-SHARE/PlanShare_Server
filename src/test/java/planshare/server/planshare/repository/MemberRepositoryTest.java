package planshare.server.planshare.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import planshare.server.planshare.domain.Member;

import javax.persistence.EntityManager;

public class MemberRepositoryTest {

    private EntityManager em;
    JpaMemberRepository memberRepository = new JpaMemberRepository(em);

    @Test
    public void save(){
        Member member = new Member();
        member.setId(1l);
        member.setEmail("spoa12@naver.com");
        member.setName("heejung");

        System.out.println(member);
        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);

    }




}
