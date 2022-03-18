package planshare.server.planshare.goal.service;

import org.junit.jupiter.api.BeforeEach;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.goal.repository.JpaGoalRepository;
import planshare.server.planshare.repository.JpaMemberRepository;
import planshare.server.planshare.repository.MemberRepository;

import javax.persistence.EntityManager;


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

    void addGoal(){

    }

}
