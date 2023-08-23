package game.textgameserver.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "BUTTON_ACTION_TBL", schema = "game1_db")
public class ButtonAction {

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

    private String description;

    private String type;

    @Column(name = "IF_CODE")
    private String ifCode;

    @Column(name = "DO_CODE")
    private String doCode;

    @Column(name = "ELSE_CODE")
    private String elseCode;

//    @Column(name = "INPUT_PARAMS_NUMBER")
//    private Integer paramsNum;
}
