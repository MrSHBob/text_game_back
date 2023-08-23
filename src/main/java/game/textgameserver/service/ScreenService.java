package game.textgameserver.service;

import game.textgameserver.dao.Screen;
import game.textgameserver.repo.ScreenRepo;
import game.textgameserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepo repo;

    public Screen getScreenById(Long id) {
        return repo.findById(id).get();
    }

    // create screen - TODO
    // edit screen - TODO
    // delete screen - TODO
    // getAllScreens - TODO
}
