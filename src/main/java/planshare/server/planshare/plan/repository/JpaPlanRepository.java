package planshare.server.planshare.plan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Plan;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class JpaPlanRepository implements PlanRepository{

    private final EntityManager em;

    @Override
    public Plan save(Plan plan) {
        if (plan.getId() == null){
            System.out.println("planRepo : create : persist :");
            em.persist(plan);
        } else {
            System.out.println("planRepo : update : merge : ");
            em.merge(plan);
        }
        return plan;
    }

    @Override
    public Optional<Plan> findById(Long id) {
        Plan plan = em.find(Plan.class, id);
        return Optional.ofNullable(plan);
    }

    @Override
    public List<Plan> findByGoal(Goal goal) {
        return em.createQuery("select p from Plan p where p.goal.id = :goalId", Plan.class)
                .setParameter("goalId", goal.getId())
                .getResultList();
    }

    @Override
    public List<Plan> findByDate(LocalDateTime date) {
        return em.createQuery("select p from Plan p where p.date = :planDate", Plan.class)
                .setParameter("planDate", date)
                .getResultList();
    }

    @Override
    public List<Plan> findAll() {
        return em.createQuery("select p from Plan p", Plan.class)
                .getResultList();
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Plan p where p.id = :planId")
                .setParameter("planId", id);
        int rows = query.executeUpdate();
        return rows;
    }

}
