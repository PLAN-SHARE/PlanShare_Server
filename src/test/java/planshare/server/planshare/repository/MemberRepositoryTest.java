package planshare.server.planshare.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.user.service.UserService;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    UserService userService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save(){

//        Member member = new Member();
//        member.setId(3l);
//        member.setEmail("email3");
////        member.setName("hoseong3");
//
////        Long saveId = userService.join(member);
//        System.out.println(saveId);
//        System.out.println(member);
//
//        Member result = memberRepository.findById(saveId).get();
//        Assertions.assertThat(member).isEqualTo(result);
    }
}
