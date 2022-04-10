package planshare.server.planshare.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    private String name;

    private LocalDate date;

    // 체크 여부
    private boolean checkStatus;

    @ManyToOne
    @JoinColumn(name = "g_id")
    private Goal goal;

}
