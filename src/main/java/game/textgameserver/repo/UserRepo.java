package game.textgameserver.repo;

import game.textgameserver.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String name);

}
