package planshare.server.planshare.goal.controller;

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
@RequestMapping("/goal")
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/create")
    public Goal create(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                       @RequestBody GoalForm goalForm){

        System.out.println("controller : "+userDetailsVO+"\n"+goalForm);
        return goalService.addGoal(userDetailsVO,goalForm);
    }

    // goal by id
    @GetMapping("/read/{goalId}")
    public Optional<Goal> readGoalById(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                  @PathVariable(name = "goalId") Long id){
        return goalService.findGoalOfId(userDetailsVO, id);
    }

    // goal of member and name
    @GetMapping("/read/myself")
    public List<Goal> listOfMyselfAndName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                          @RequestParam(required = false) String name){
        if(name == null){
            return goalService.findMyGoals(userDetailsVO);
        }
        return goalService.findMyGoalsOfName(userDetailsVO, name);
    }

    @GetMapping("/read/member/{memberId}")
    public List<Goal> listOfMemberAndName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                   @RequestParam(required = false) String name,
                                   @PathVariable(name = "memberId") Long memberId){
        if(name == null){
            return goalService.findGoalsOfMember(memberId);
        }
        return goalService.findGoalsOfMemberAndName(memberId, name);
    }

    // goal of name
    @GetMapping("/read/alluser")
    public List<Goal> listOfName(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                                 @RequestParam(required = false) String name){
        if(name == null){
            return goalService.findGoals();
        }
        return goalService.findGoalsOfName(name);
    }

    // goal update
    @PutMapping("/update/{goalId}")
    public Goal modifyGoal(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                           @RequestBody GoalForm goalForm,
                           @PathVariable(name = "goalId") Long id){
        return goalService.updateGoalName(userDetailsVO, id, goalForm);
    }

    // goal delete
    @DeleteMapping("/delete/{goalId}")
    public int deleteGoal(@AuthenticationPrincipal CustomUserDetailsVO userDetailsVO,
                          @PathVariable(name = "goalId") Long id){
        return goalService.deleteGoal(userDetailsVO, id);
    }

}
