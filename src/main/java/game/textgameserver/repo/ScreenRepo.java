package game.textgameserver.repo;

import game.textgameserver.dao.Screen;
import game.textgameserver.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface ScreenRepo extends CrudRepository<Screen, Long> {
    Screen findByType(String type);
}
