package planshare.server.planshare.domain;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    private String name;

    private LocalDate date;

    // 체크 여부
    private boolean checkStatus;

    @ManyToOne
    @JoinColumn(name = "g_id")
    private Goal goal;

    public static Plan createPlan(String name, LocalDate date, boolean checkStatus, Goal goal){
        Plan plan = new Plan();
        plan.name = name;
        plan.date = date;
        plan.checkStatus = checkStatus;
        plan.goal = goal;

        return plan;
    }

    public void modifyName(String name){
        this.name = name;
    }

    public void modifyCheck(boolean checkStatus){
        this.checkStatus = checkStatus;
    }

}
