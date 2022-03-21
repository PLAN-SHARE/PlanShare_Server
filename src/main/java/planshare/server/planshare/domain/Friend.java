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

    public static Friend createFriend(Member fromMember, Member toMember) {
        Friend friend = new Friend();
        friend.fromMember = fromMember;
        friend.toMember = toMember;

        return friend;
    }
}
