package game.textgameserver.dao;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "SCREEN_TBL", schema = "game1_db")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    private Integer version;

    private String name;

    private String type;

    private String description;

    @Column(name = "ILLUSTRATION_LINK")
    private String illustrationLink;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCREEN_ID")
    private List<PlotButton> buttons;
}
