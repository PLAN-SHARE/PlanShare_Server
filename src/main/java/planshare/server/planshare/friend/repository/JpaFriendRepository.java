package planshare.server.planshare.friend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import planshare.server.planshare.domain.Friend;
import planshare.server.planshare.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class JpaFriendRepository implements FriendRepository {

    private final EntityManager em;

    @Override
    public Friend save(Friend friend) {
        em.persist(friend);
        return friend;
    }

    @Override
    public void delete(Friend friend) {
        Friend entity = em.find(Friend.class, friend.getId());
        em.remove(entity);
    }

    @Override
    public Optional<Friend> findByFromMemberAndToMember(Member fromMember, Member toMember) {
        return em.createQuery("select f from Friend f where f.fromMember = :fromMember and f.toMember = :toMember", Friend.class)
                .setParameter("fromMember", fromMember)
                .setParameter("toMember", toMember)
                .getResultStream().findFirst();
    }

    @Override
    public List<Member> findByFromMember(Member fromMember) {
        return em.createQuery("select f.toMember from Friend f where f.fromMember = :fromMember", Member.class)
                .setParameter("fromMember", fromMember)
                .getResultList();
    }

    @Override
    public List<Member> findByToMember(Member toMember) {
        return em.createQuery("select f.fromMember from Friend f where f.toMember = :toMember", Member.class)
                .setParameter("toMember", toMember)
                .getResultList();
    }
}
