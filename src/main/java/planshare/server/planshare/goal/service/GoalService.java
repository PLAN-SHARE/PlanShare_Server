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

        Goal goal = Goal.createGoal(form.getName(), form.getColor(), form.getIcon(), form.isVisibility(), member.get());

        System.out.println("service : "+member);
        System.out.println("service : "+goal);

        return goalRepository.save(goal);
    }

    /**
     * my total goal search
     */
    public List<Goal> findMyGoals(CustomUserDetailsVO cud){
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());

        return goalRepository.findByMember(member.get());
    }

    /**
     * my goal search by name
     */
    public List<Goal> findMyGoalsOfName(CustomUserDetailsVO cud, String name){
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());

        return goalRepository.findByMemberAndName(member.get(), name);
    }

    /**
     * goal search by memberId
     */
    public List<Goal> findGoalsOfMember(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()) {
            return goalRepository.findByMember(member.get());
        } else {
            return null;
        }
    }

    /**
     * goal search by memberId and name
     */
    public List<Goal> findGoalsOfMemberAndName(Long memberId, String name){
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()) {
            return goalRepository.findByMemberAndName(member.get(), name);
        } else {
            return null;
        }
    }

    /**
     * total goal search
     */
    public List<Goal> findGoals(){
        return goalRepository.findAll();
    }

    /**
     * goal search by name
     */
    public List<Goal> findGoalsOfName(String name){

        return goalRepository.findByName(name);
    }

    /**
     * goal search by id
     */
    public Optional<Goal> findGoalOfId(CustomUserDetailsVO cud, Long goalId){

        return goalRepository.findById(goalId);
    }

    /**
     * goal name update
     */
    public Goal updateGoalName(CustomUserDetailsVO cud, Long goalId, GoalForm goalForm){

        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);

        if(goal.isPresent() && member.get().getId() == goal.get().getMember().getId()){
            goal.get().modifyGoal(goalForm.getName(), goalForm.getColor(), goalForm.getIcon(), goalForm.isVisibility());

            return goalRepository.save(goal.get());
        } else {
            return null;
        }


    }

    /**
     * goal delete
     */
    public int deleteGoal(CustomUserDetailsVO cud, Long goalId){

        int rows = 0;
        Optional<Member> member = memberRepository.findByEmail(cud.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);

        if(goal.isPresent() && member.get().getId() == goal.get().getMember().getId()){
            rows = goalRepository.deleteById(goalId);
        }
        return rows;
    }

}
