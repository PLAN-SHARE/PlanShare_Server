package planshare.server.planshare.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlanDate {

    private String date;

    private List<PlanEx> planExList;

    public static PlanDate createPlanDate(String date, List<PlanEx> planExList){
        PlanDate planDate = new PlanDate();
        planDate.date = date;
        planDate.planExList = planExList;

        return planDate;
    }

}
