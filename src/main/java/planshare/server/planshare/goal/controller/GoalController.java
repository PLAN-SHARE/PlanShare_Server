package planshare.server.planshare.goal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.goal.dto.GoalForm;
import planshare.server.planshare.goal.service.GoalService;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

@RequiredArgsConstructor
@RestController
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/Goal/")
    public String create(@AuthenticationPrincipal CustomUserDetailsVO cud, GoalForm form){

        goalService.addGoal(cud,form);

        return "";
    }

}
