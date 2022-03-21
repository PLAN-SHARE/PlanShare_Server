package planshare.server.planshare.friend.repository;

import planshare.server.planshare.domain.Friend;
import planshare.server.planshare.domain.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    Friend save(Friend friend);
    void delete(Friend friend);
    Optional<Friend> findByFromMemberAndToMember(Member fromMember, Member toMember);
    List<Member> findByFromMember(Member fromMember);
    List<Member> findByToMember(Member toMember);
}
