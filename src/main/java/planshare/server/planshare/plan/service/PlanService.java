package planshare.server.planshare.plan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.domain.Plan;
import planshare.server.planshare.goal.repository.GoalRepository;
import planshare.server.planshare.plan.dto.PlanDate;
import planshare.server.planshare.plan.dto.PlanEx;
import planshare.server.planshare.plan.dto.PlanForm;
import planshare.server.planshare.plan.repository.PlanRepository;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
     * select plan list by date
     * 일별로 묶어서 반환
     */
    public List<PlanDate> findPlanByDate(CustomUserDetailsVO userDetailsVO, Long memberId, int year, int month){

        Optional<Member> user = memberRepository.findByEmail(userDetailsVO.getUsername());  // 요청한 사용자
        Optional<Member> member = memberRepository.findById(memberId);                      // 요청된 멤버
        List<Goal> goalList = goalRepository.findByMember(member.get());

        Map<String, List<PlanEx>> planMap = new HashMap<>();
        List<Plan> planList = new ArrayList<>();

        for (Goal goal : goalList){
            if(goal.isVisibility() || user.get().getId() == goal.getMember().getId()){
                planList.addAll(planRepository.findByGoal(goal));
            }
        }
        for (Plan plan : planList){
            LocalDate localDate = plan.getDate().toLocalDate();
            if (localDate.getYear() == year && localDate.getMonthValue() == month){
                String convertedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                List<PlanEx> planExes = new ArrayList<>();
                List<PlanEx> planExList = planMap.getOrDefault(convertedDate, planExes);

                PlanEx planEx = PlanEx.createPlanEx(plan.getId(), plan.getName(), plan.isCheckStatus(), plan.getGoal());
                planExList.add(planEx);
                planMap.put(convertedDate, planExList);
            }
        }

        List<PlanDate> planDateList = new ArrayList<>();
        for (String key : planMap.keySet()){
            PlanDate planDate = PlanDate.createPlanDate(key, planMap.get(key));
            planDateList.add(planDate);
        }

        return planDateList;
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
