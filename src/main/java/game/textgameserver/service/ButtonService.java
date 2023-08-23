package game.textgameserver.service;

import game.textgameserver.dao.PlotButton;
import game.textgameserver.repo.PlotButtonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ButtonService {

    @Autowired
    private PlotButtonRepo repo;

    public PlotButton getButtonById(Long id) {
        return repo.findById(id).get();
    }

    public List<PlotButton> getAllButtons() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).toList();
    }

    public List<PlotButton> getButtonsByScreenId(Long screenId) {
        return StreamSupport.stream(repo.findAllByScreenId(screenId).spliterator(), false).toList();
    }
}
