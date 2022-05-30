package planshare.server.planshare.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import planshare.server.planshare.domain.Goal;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlanEx {

    private Long id;

    private String name;

    private boolean checkStatus;

    private Goal goal;

    public static PlanEx createPlanEx(Long id, String name, boolean checkStatus, Goal goal){
        PlanEx planEx = new PlanEx();
        planEx.id = id;
        planEx.name = name;
        planEx.checkStatus = checkStatus;
        planEx.goal = goal;

        return planEx;
    }
}
