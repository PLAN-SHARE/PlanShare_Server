package planshare.server.planshare.goal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class JpaGoalRepository implements GoalRepository {

    private final EntityManager em;

    @Override
    public Goal save(Goal goal) {
        System.out.println("repo : "+goal);
        if (goal.getId() == null) {
            System.out.println("repo : create : persist : ");
            em.persist(goal);
        } else {
            System.out.println("repo : update : merge : ");
            em.merge(goal);
        }
        return goal;
    }

    @Override
    public Optional<Goal> findById(Long id) {
        Goal goal = em.find(Goal.class, id);
        return Optional.ofNullable(goal);
    }

    @Override
    public List<Goal> findByName(String name) {
        return em.createQuery("select g from Goal g where g.name = :name",Goal.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Goal> findByMember(Member member) {
        return em.createQuery("select g from Goal g where g.member.id = :memberId",Goal.class)
                .setParameter("memberId",member.getId())
                .getResultList();
    }

    @Override
    public List<Goal> findByMemberAndName(Member member, String name) {
        return em.createQuery("select g from Goal g where g.member.id = :memberId and g.name = :name", Goal.class)
                .setParameter("memberId", member.getId())
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Goal> findAll() {
        return em.createQuery("select g from Goal g", Goal.class)
                .getResultList();
    }

    @Override
    public int deleteById(long goalId) {
        Query query = em.createQuery("delete from Goal g where g.id = :goalId")
                .setParameter("goalId",goalId);
        int rows = query.executeUpdate();
        return rows;
    }


}
