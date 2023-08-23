package game.textgameserver.repo;

import game.textgameserver.dao.GameState;
import game.textgameserver.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface GameStateRepo extends CrudRepository<GameState, Long> {

    GameState findByUser(User user);
//    int deleteAllByUser(User user);

}
