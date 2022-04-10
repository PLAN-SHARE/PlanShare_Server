package planshare.server.planshare.plan.repository;

import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Plan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    Plan save(Plan plan);
    Optional<Plan> findById(Long id);
    List<Plan> findByGoal(Goal goal);
    List<Plan> findByDate(LocalDate date);
    List<Plan> findAll();
    int deleteById(Long id);


}
