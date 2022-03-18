package planshare.server.planshare.goal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.goal.dto.GoalForm;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    /**
     * goal store
     */
    public Goal addGoal(CustomUserDetailsVO cud, GoalForm form){

        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());

        Goal goal = Goal.createGoal(form.getName(), member.get());

        System.out.println("service : "+member);
        System.out.println("service : "+goal);

        return goalRepository.save(goal);
    }

    /**
     * 전체 goal 조회
     */
    public List<Goal> findGoals(){
        return goalRepository.findAll();
    }

    /**
     * 한 member의 goals 전체 조회
     */
    public List<Goal> findGoalsOfMember(CustomUserDetailsVO cud){
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());

        return goalRepository.findByMember(member.get());
    }

    /**
     * 한 member의 goal 이름 조회
     */
    public List<Goal> findGoalsOfMemberAndName(CustomUserDetailsVO cud, String name){
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());

        return goalRepository.findByMemberAndName(member.get(), name);
    }

    /**
     * goal check by name
     */
    public List<Goal> findGoalsOfName(String name){

        return goalRepository.findByName(name);
    }

    /**
     * goal check by id
     */
    public Optional<Goal> findGoalOfId(CustomUserDetailsVO cud, Long goalId){

        return goalRepository.findById(goalId);
    }

    /**
     * goal name update
     */
    public Goal updateGoalName(CustomUserDetailsVO cud, Long goalId, String name){

        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());
        Goal goal = goalRepository.findById(goalId).get();

        if(member.get().getId() == goal.getMember().getId()){
            goal.modifyName(name);
        }

        return goalRepository.save(goal);
    }

    public int deleteGoal(CustomUserDetailsVO cud, Long goalId){

        int rows = 0;
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());
        Goal goal = goalRepository.findById(goalId).get();

        if(member.get().getId() == goal.getMember().getId()){
            rows = goalRepository.deleteById(goalId);
        }
        return rows;
    }


}
