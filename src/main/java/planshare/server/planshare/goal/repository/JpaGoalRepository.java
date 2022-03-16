package planshare.server.planshare.goal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;

import javax.persistence.EntityManager;
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
        em.persist(goal);
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
}
