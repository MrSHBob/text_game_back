package game.textgameserver.dao;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PLOT_BUTTONS_TBL", schema = "game1_db")
public class PlotButton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    private Integer version;

//    @ManyToOne
//    @JoinColumn(name = "SCREEN_ID")
//    private Screen screen;

    @Column(name = "SCREEN_ID")
    private Long screenId;

    private String name;

    private String type;

    private String actions;     // string of commands with delimiter ","

    private String description;
}
