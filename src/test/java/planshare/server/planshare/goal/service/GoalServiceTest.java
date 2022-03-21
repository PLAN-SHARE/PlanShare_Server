package planshare.server.planshare.goal.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.goal.repository.JpaGoalRepository;
import planshare.server.planshare.repository.JpaMemberRepository;
import planshare.server.planshare.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
public class GoalServiceTest {

    EntityManager em;
    GoalRepository goalRepository;
    MemberRepository memberRepository;
    GoalService goalService;

    @BeforeEach
    public void beforeEach(){
        goalRepository = new JpaGoalRepository(em);
        memberRepository = new JpaMemberRepository(em);
        goalService = new GoalService(goalRepository, memberRepository);
    }

    @Test
    void optional(){
        Member m = null;
        Optional<Member> member = Optional.ofNullable(m);
        if(member.isPresent()){
            System.out.println("present : "+member.isPresent());
            System.out.println("empty : "+member.isEmpty());
        }
        if(member.isEmpty()){
            System.out.println("present : "+member.isPresent());
            System.out.println("empty : "+member.isEmpty());
        }
    }

}
