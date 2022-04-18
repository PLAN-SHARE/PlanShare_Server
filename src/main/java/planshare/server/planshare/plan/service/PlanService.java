package planshare.server.planshare.plan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.domain.Plan;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.plan.dto.PlanForm;
import planshare.server.planshare.plan.repository.PlanRepository;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    /**
     * plan store
     */
    public Plan addPlan(CustomUserDetailsVO userDetailsVO, Long goalId, PlanForm planForm){

        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);

        // 사용자와 goal 사용자가 같은 지 확인
        if(member.get().getId() == goal.get().getMember().getId()){
            Plan plan = Plan.createPlan(planForm.getName(), planForm.getDate(), false, goal.get());

            System.out.println("service : "+goalId);
            return planRepository.save(plan);
        } else{
            return null;
        }
    }

    /**
     * select plan by planId
     */
    public Optional<Plan> findPlanOfId(CustomUserDetailsVO userDetailsVO, Long goalId, Long planId){

        Optional<Plan> plan = planRepository.findById(planId);
        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());

        if (plan.get().getGoal().getId() == goalId && goal.get().isVisibility()){   // 전체 공개 경우
            return plan;
        } else if (member.get().getId() == goal.get().getMember().getId()){         // 나만 보기 경우
            return plan;
        } else {
            return null;
        }
    }

    /**
     * select plan list by goalId
     */
    public List<Plan> findPlansOfGoal(CustomUserDetailsVO userDetailsVO, Long goalId){

        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());

        if(goal.isPresent()){
            if(goal.get().isVisibility() || member.get().getId() == goal.get().getMember().getId()){
                return planRepository.findByGoal(goal.get());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * plan name update
     */
    public Plan updatePlanName(CustomUserDetailsVO userDetailsVO, Long goalId, Long planId, String name){

        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<Plan> plan = planRepository.findById(planId);

        if (member.get().getId() == goal.get().getMember().getId() && goalId == plan.get().getGoal().getId()){
            plan.get().modifyName(name);

            return planRepository.save(plan.get());
        } else {
            return null;
        }
    }

    /**
     * plan checkStatus update
     */
    public Plan updatePlanStatus(CustomUserDetailsVO userDetailsVO, Long goalId, Long planId){

        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<Plan> plan = planRepository.findById(planId);

        if (member.get().getId() == goal.get().getMember().getId() && goalId == plan.get().getGoal().getId()){
            if (plan.get().isCheckStatus() == false){
                plan.get().modifyCheck(true);
            } else {
                plan.get().modifyCheck(false);
            }
            return planRepository.save(plan.get());
        } else {
            return null;
        }
    }

    /**
     * plan delete
     */
    public int deletePlan(CustomUserDetailsVO userDetailsVO, Long goalId, Long planId){

        int rows = 0;
        Optional<Member> member = memberRepository.findByEmail(userDetailsVO.getUsername());
        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<Plan> plan = planRepository.findById(planId);

        if (member.get().getId() == goal.get().getMember().getId() && goalId == plan.get().getGoal().getId()){
           rows = planRepository.deleteById(planId);
        }
        return rows;
    }

}
