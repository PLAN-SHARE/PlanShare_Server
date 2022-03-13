package planshare.server.planshare.goal.repository;

import planshare.server.planshare.domain.Goal;
import planshare.server.planshare.domain.Member;

import java.util.List;
import java.util.Optional;

public interface GoalRepository {

    Goal save(Goal goal);
    Optional<Goal> findById(Long id);
    List<Goal> findByName(String name);
    List<Goal> findByMember(Member member);
    List<Goal> findByMemberAndName(Member member, String name);
    List<Goal> findAll();

}
