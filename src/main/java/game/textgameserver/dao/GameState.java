package game.textgameserver.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "GAME_STATE_TBL", schema = "game1_db")
public class GameState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    private Integer version;

    @OneToOne
    @JoinColumn(name = "OWNER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "ACTIVE_SCREEN_ID")  // , referencedColumnName = "id"
    private Screen activeScreen;

    @Column(name = "CHAR_NAME")
    private String charName;

    @Column(name = "CHAR_RACE")
    private String charRace;

    @Column(name = "CHAR_CLASS")
    private String charClass;

    @Column(name = "CHAR_STRENGTH")
    private Integer charStrength;

    @Column(name = "CHAR_AGILITY")
    private Integer charAgility;

    @Column(name = "CHAR_ENDURANCE")
    private Integer charEndurance;

    @Column(name = "CHAR_INTELLIGENCE")
    private Integer charIntelligence;

    @Column(name = "TAB_MAP_PIC_LINK")
    private String tabMapPicLink;

    @Column(name = "TAB_CHAR_PIC_LINK")
    private String tabCharPicLink;
}
