package planshare.server.planshare.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Member toMember;

    // false: 친구 요청 상태
    // true: 친구 맺어진 상태
    private Boolean status;

    public static Friend createFriend(Member fromMember, Member toMember, Boolean status) {
        Friend friend = new Friend();
        friend.fromMember = fromMember;
        friend.toMember = toMember;
        friend.status = status;

        return friend;
    }

}
