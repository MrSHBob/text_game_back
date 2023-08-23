package game.textgameserver.repo;

import game.textgameserver.dao.ButtonAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ButtonActionRepo extends CrudRepository<ButtonAction, Long> {

    @Query("SELECT t FROM ButtonAction t WHERE t.type like \"0%\" and t.type like \"%0\"")
    List<ButtonAction> selectSimpleActions();

}
