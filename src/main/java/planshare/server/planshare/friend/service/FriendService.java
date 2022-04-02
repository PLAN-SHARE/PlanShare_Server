package planshare.server.planshare.friend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planshare.server.planshare.domain.Friend;
import planshare.server.planshare.domain.Member;
import planshare.server.planshare.friend.dto.ProfileDTO;
import planshare.server.planshare.friend.repository.FriendRepository;
import planshare.server.planshare.repository.MemberRepository;
import planshare.server.planshare.user.dto.CustomUserDetailsVO;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    // email로 친구 프로필 찾기
    public ProfileDTO search(String email, CustomUserDetailsVO userDetails) {

        Optional<Member> fromMember = memberRepository.findByEmail(userDetails.getUsername());
        Optional<Member> toMember = memberRepository.findByEmail(email);

        // 없는 회원인 경우
        if (toMember.isEmpty()) {
            return null;
        }
        Optional<Friend> follow = friendRepository.findByFromMemberAndToMember(fromMember.get(), toMember.get());

        // 팔로우한 경우
        if (follow.isPresent()) {
            return new ProfileDTO(toMember.get(), true);
        }
        // 팔로우 아직 안한 경우
        return new ProfileDTO(toMember.get(), false);
    }

    // 친구 follow 함
    public ProfileDTO follow(String toMemberEmail, CustomUserDetailsVO userDetails) {

        Optional<Member> fromMember = memberRepository.findByEmail(userDetails.getUsername());
        Optional<Member> toMember = memberRepository.findByEmail(toMemberEmail);

        Friend friend = Friend.createFriend(fromMember.get(), toMember.get());
        friendRepository.save(friend);

        return new ProfileDTO(toMember.get(), true);
    }

    // 친구 unfollow 함
    public ProfileDTO unfollow(String toMemberEmail, CustomUserDetailsVO userDetails) {

        Optional<Member> fromMember = memberRepository.findByEmail(userDetails.getUsername());
        Optional<Member> toMember = memberRepository.findByEmail(toMemberEmail);
        Optional<Friend> follow = friendRepository.findByFromMemberAndToMember(fromMember.get(), toMember.get());

        if (follow.isEmpty()) {
            return null; // 예외처리 나중에 제대로 해주기..
        }
        friendRepository.delete(follow.get());

        return new ProfileDTO(toMember.get(), false);
    }

    // 내 follow 목록
    public List<Member> findFollow(CustomUserDetailsVO userDetails) {

        Optional<Member> member = memberRepository.findByEmail(userDetails.getUsername());
        return friendRepository.findByFromMember(member.get());
    }

    // 내 following 목록
    public List<Member> findFollowing(CustomUserDetailsVO userDetails) {

        Optional<Member> member = memberRepository.findByEmail(userDetails.getUsername());
        return friendRepository.findByToMember(member.get());
    }
}
