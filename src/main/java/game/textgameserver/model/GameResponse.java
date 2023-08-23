package game.textgameserver.model;

import game.textgameserver.dao.GameState;
import game.textgameserver.service.GameStateMessageStorage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@NoArgsConstructor
@Data
public class GameResponse {
    GameState gameState;
    String message;
}
