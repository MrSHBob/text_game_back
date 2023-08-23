package game.textgameserver.service;

import game.textgameserver.dao.GameState;
import game.textgameserver.dao.User;
import game.textgameserver.repo.GameStateRepo;
import game.textgameserver.repo.ScreenRepo;
import game.textgameserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameStateRepo gsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ScreenRepo screenRepo;

    public GameState getGameStateByOwnerId(Long ownerId) {
        GameState gs = null;
        User u = userRepo.findById(ownerId).get();
        try {
            gs = gsRepo.findByUser(u);
        } catch (Exception e) {
            gs = newGameStateForOwner(u);
        }
        return gs;
    }

    public GameState newGameStateForOwner(User user) {
        GameState gs = null;

        try {
            gs = gsRepo.findByUser(user);
        } catch(Exception e) { }

        if (gs == null) {
            gs = new GameState();
        }

        gs.setCreated(LocalDateTime.now());
        gs.setLastUpdateDate(LocalDateTime.now());
        gs.setVersion(0);
        gs.setUser(user);
        gs.setActiveScreen(screenRepo.findByType("default"));
        gs.setCharStrength(5);
        gs.setCharAgility(5);
        gs.setCharEndurance(5);
        gs.setCharIntelligence(5);

        return gsRepo.save(gs);
    }

    public GameState updateGameState(GameState update) {
        GameState gs = gsRepo.findByUser(update.getUser());

        gs.setLastUpdateDate(LocalDateTime.now());
        gs.setVersion(gs.getVersion() + 1);
        if (gs.getId() != update.getId()) throw new RuntimeException("Game State Update Failed");
        gs.setActiveScreen(update.getActiveScreen());
        gs.setCharStrength(update.getCharStrength());
        gs.setCharAgility(update.getCharAgility());
        gs.setCharEndurance(update.getCharEndurance());
        gs.setCharIntelligence(update.getCharIntelligence());

        return gsRepo.save(gs);
    }
}
