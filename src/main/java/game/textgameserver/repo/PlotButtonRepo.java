package game.textgameserver.repo;

import game.textgameserver.dao.GameState;
import game.textgameserver.dao.PlotButton;
import game.textgameserver.dao.Screen;
import game.textgameserver.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlotButtonRepo extends CrudRepository<PlotButton, Long> {

    List<PlotButton> findAllByScreenId(Long screenId);

}
