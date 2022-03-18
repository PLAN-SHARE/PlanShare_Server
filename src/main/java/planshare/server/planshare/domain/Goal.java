package planshare.server.planshare.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member member;

    public static Goal createGoal(String name, Member member){
        Goal goal = new Goal();
        goal.name = name;
        goal.member = member;

        return goal;
    }

    public void modifyName(String name){
        this.name = name;
    }

}
