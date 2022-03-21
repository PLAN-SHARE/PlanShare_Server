package planshare.server.planshare.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long id;

    private Long kakaoId;

    private String email;

    private String nickName;

    // set메소드는 절대로 쓰면 안됩니다... 실제로 이렇게 파라미터로 넘겨서 수정하거나 만들도록 변경해야합니다.
    public static Member createMember(Long kakaoId, String email, String nickName) {
        Member member = new Member();
        member.kakaoId = kakaoId;
        member.email = email;
        member.nickName = nickName;

        return member;
    }

}
