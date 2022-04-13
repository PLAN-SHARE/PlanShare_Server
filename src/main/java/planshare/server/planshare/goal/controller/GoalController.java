package planshare.server.planshare.goal.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.goal.dto.GoalForm;
import planshare.server.planshare.goal.service.GoalService;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class GoalController {

    private final GoalService goalService;

    @ApiOperation(value = "goal 생성 API", notes = "생성한 goal 객체 반환")
    @PostMapping("/goals")
    public Goal create(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                       @RequestBody GoalForm goalForm){

        System.out.println("controller : "+userDetailsVO+"\n"+goalForm);
        return goalService.addGoal(userDetailsVO,goalForm);
    }

    // goal by id
    @ApiOperation(value = "goal 하나 조회(by goalId) API", notes = "찾은 goal 객체 반환")
    @GetMapping("/goals/{goalId}")
    public Optional<Goal> readGoalById(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                  @PathVariable(name = "goalId") Long id){
        return goalService.findGoalOfId(userDetailsVO, id);
    }

    // goal of member and name
    @ApiOperation(value = "나의 goal 목록 조회 API", notes = "name 파라미터 추가시(필수x) 해당 이름의 goal 리스트 반환")
    @GetMapping("/goals/my-goals")
    public List<Goal> listOfMyselfAndName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                          @RequestParam(required = false) String name){
        if(name == null){
            return goalService.findMyGoals(userDetailsVO);
        }
        return goalService.findMyGoalsOfName(userDetailsVO, name);
    }

    @ApiOperation(value = "특정 멤버의 goal 목록 조회 API", notes = "name 파라미터 추가시(필수x) 해당 이름의 goal 리스트 반환")
    @GetMapping("/goals/members/{memberId}")
    public List<Goal> listOfMemberAndName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                   @RequestParam(required = false) String name,
                                   @PathVariable(name = "memberId") Long memberId){
        if(name == null){
            return goalService.findGoalsOfMember(memberId);
        }
        return goalService.findGoalsOfMemberAndName(memberId, name);
    }

    // goal of name
    @ApiOperation(value = "모든 멤버의 goal 목록 조회 API", notes = "name 파라미터 추가시(필수x) 해당 이름의 goal 리스트 반환")
    @GetMapping("/goals")
    public List<Goal> listOfName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                 @RequestParam(required = false) String name){
        if(name == null){
            return goalService.findGoals();
        }
        return goalService.findGoalsOfName(name);
    }

    // goal update
    @ApiOperation(value = "특정 goal 내용 수정 API", notes = "수정한 goal 객체 반환")
    @PutMapping("/goals/{goalId}")
    public Goal modifyGoal(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                           @RequestBody GoalForm goalForm,
                           @PathVariable(name = "goalId") Long id){
        return goalService.updateGoalName(userDetailsVO, id, goalForm);
    }

    // goal delete
    @ApiOperation(value = "특정 goal 삭제 API", notes = "삭제 성공시 정수 1 반환 / 실패시 0 반환")
    @DeleteMapping("/goals/{goalId}")
    public int deleteGoal(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                          @PathVariable(name = "goalId") Long id){
        return goalService.deleteGoal(userDetailsVO, id);
    }

}
