package planshare.server.planshare.plan.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import planshare.server.planshare.domain.Plan;
import planshare.server.planshare.plan.dto.PlanEx;
import planshare.server.planshare.plan.dto.PlanForm;
import planshare.server.planshare.plan.dto.PlanName;
import planshare.server.planshare.plan.service.PlanService;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PlanController {

    private final PlanService planService;

    @ApiOperation(value = "plan 생성 API", notes = "생성한 plan 객체 반환")
    @PostMapping("/goals/{goalId}/plans")
    public Plan create(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                       @PathVariable(name = "goalId") Long goalId,
                       @RequestBody PlanForm planForm){
        System.out.println("planController : goalId : "+goalId+" , body "+planForm);
        return planService.addPlan(userDetailsVO, goalId, planForm);
    }

    @ApiOperation(value = "plan 하나 조회(by goalId + planId) API", notes = "찾은 plan 객체 반환")
    @GetMapping("/goals/{goalId}/plans/{planId}")
    public Optional<Plan> readPlanByPlanId(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                           @PathVariable(name = "goalId") Long goalId,
                                           @PathVariable(name = "planId") Long planId){
        return planService.findPlanOfId(userDetailsVO, goalId, planId);
    }

    @ApiOperation(value = "해당 goal에 속한 plan 목록 조회 API", notes = "plan 리스트 반환")
    @GetMapping("/goals/{goalId}/plans")
    public List<Plan> readPlansByGoal(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                      @PathVariable(name = "goalId") Long goalId){
        return planService.findPlansOfGoal(userDetailsVO, goalId);
    }

    @ApiOperation(value = "해당 goal에 속하고 months에 해당하는 달의 plan 목록 조회 API", notes = "일별로 정렬한 plan 리스트 반환")
    @GetMapping("/users/{memberId}/goals/plans/years/{year}/months/{month}")
    public Map<String, List<PlanEx>> readPlanByDate(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                                       @PathVariable(name = "memberId") Long memberId,
                                                       @PathVariable(name = "year") int year,
                                                       @PathVariable(name = "month") int month){
        return planService.findPlanByDate(userDetailsVO, memberId, year, month);
    }

    @ApiOperation(value = "특정 plan 이름 수정 API", notes = "수정한 plan 객체 반환")
    @PutMapping("/goals/{goalId}/plans/{planId}")
    public Plan modifyNameOfPlan(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                 @PathVariable(name = "goalId") Long goalId,
                                 @PathVariable(name = "planId") Long planId,
                                 @RequestBody PlanName planName){
        return planService.updatePlanName(userDetailsVO, goalId, planId, planName.getName());
    }

    @ApiOperation(value = "특정 plan 완료여부 수정 API", notes = "수정한 plan 객체 반환")
    @PutMapping("/goals/{goalId}/plans/{planId}/check")
    public Plan modifyCheckOfPlan(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                  @PathVariable(name = "goalId") Long goalId,
                                  @PathVariable(name = "planId") Long planId){
        return planService.updatePlanStatus(userDetailsVO, goalId, planId);
    }

    @ApiOperation(value = "특정 plan 삭제 API", notes = "삭제 성공시 정수 1 반환 / 실패시 0 반환")
    @DeleteMapping("/goals/{goalId}/plans/{planId}")
    public int deletePlan(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                          @PathVariable(name = "goalId") Long goalId,
                          @PathVariable(name = "planId") Long planId){
        return planService.deletePlan(userDetailsVO, goalId, planId);
    }

}
