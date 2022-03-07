package planshare.server.planshare.repository;

import planshare.server.planshare.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();

}
