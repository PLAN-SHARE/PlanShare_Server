package planshare.server.planshare.goal.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.goal.repository.JpaGoalRepository;
import planshare.server.planshare.repository.JpaMemberRepository;
import planshare.server.planshare.repository.MemberRepository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class GoalRepositoryTest {

    EntityManager em;
    GoalRepository goalRepository;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        goalRepository = new JpaGoalRepository(em);
        memberRepository = new JpaMemberRepository(em);
    }

    void addGoal(){

    }

}
