package game.textgameserver.service;

import game.textgameserver.dao.ButtonAction;
import game.textgameserver.repo.ButtonActionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ButtonActionService {

    @Autowired
    private ButtonActionRepo repo;

    public ButtonAction getActionById(Long id) {
        return repo.findById(id).get();
    }

    public List<ButtonAction> getAllActions() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).toList();
    }

    public List<ButtonAction> getSimpleActions() {
        return StreamSupport.stream(repo.selectSimpleActions().spliterator(), false).toList();
    }
}
