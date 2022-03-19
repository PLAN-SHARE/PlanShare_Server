package planshare.server.planshare.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_id")
    private Long id;

    private String name;

    // HEX COLOR CODE;
    private String color;

    private int iconNumber;

    // 공개 여부
    private boolean visibility;

    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member member;

    public static Goal createGoal(String name, String color, int iconNumber, boolean visibility, Member member){
        Goal goal = new Goal();
        goal.name = name;
        goal.color = color;
        goal.iconNumber = iconNumber;
        goal.visibility = visibility;
        goal.member = member;

        return goal;
    }

    public void modifyName(String name){
        this.name = name;
    }

    public void modifyGoal(String name, String color, int iconNumber, boolean visibility){
        this.name = name;
        this.color = color;
        this.iconNumber =iconNumber;
        this.visibility = visibility;
    }
}
