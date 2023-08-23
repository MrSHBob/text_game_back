package game.textgameserver.db;

import game.textgameserver.TextGameServerApplication;
import game.textgameserver.dao.*;
import game.textgameserver.repo.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TextGameServerApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoRequests {
    StringBuilder errors = null;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ScreenRepo screenRepo;
    @Autowired
    private PlotButtonRepo plotButtonRepo;
    @Autowired
    private GameStateRepo gameStateRepo;
    @Autowired
    private ButtonActionRepo buttonActionRepo;

    protected User createUser(String name) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(name);
        user.setRole("user");
        user.setCreated(LocalDateTime.now());
        user.setLastUpdateDate(LocalDateTime.now());
        user.setVersion(0);
        return userRepo.save(user);
    }

    protected User getUserById(Long id) {
        return userRepo.findById(id).get();
    }

    protected List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        userRepo.findAll().forEach(user -> {
            result.add(user);
        });

        return result;
    }

    protected void deleteUser(Long id) {
        userRepo.delete(userRepo.findById(id).get());
    }

    protected Screen createScreen(
            String name,
            String type,
            String description,
            String illustrationLink
    ) {
        Screen scr = new Screen();
        scr.setCreated(LocalDateTime.now());
        scr.setLastUpdateDate(LocalDateTime.now());
        scr.setVersion(0);
        scr.setType(type);
        scr.setName(name);
        scr.setDescription(description);
        scr.setIllustrationLink(illustrationLink);

        return screenRepo.save(scr);
    }

    protected Screen getScreenById(Long id) {
        return screenRepo.findById(id).get();
    }

    protected List<Screen> getAllScreens() {
        List<Screen> result = new ArrayList<>();

        screenRepo.findAll().forEach(scr -> {
            result.add(scr);
        });

        return result;
    }

    protected void deleteScreen(Long id) {
        screenRepo.delete(screenRepo.findById(id).get());
    }

    protected GameState createGameState(
            Long ownerId,
            Long screenId,
            String name,
            String race,
            String theClass,
            Integer strength,
            Integer agility,
            Integer endurance,
            Integer intelligence,
            String mapPicLink
    ) {
        GameState gs = new GameState();
        gs.setCreated(LocalDateTime.now());
        gs.setLastUpdateDate(LocalDateTime.now());
        gs.setVersion(0);
        gs.setUser(getUserById(ownerId));
        gs.setActiveScreen(getScreenById(screenId));
        gs.setCharName(name);
        gs.setCharRace(race);
        gs.setCharClass(theClass);
        gs.setCharStrength(strength);
        gs.setCharAgility(agility);
        gs.setCharEndurance(endurance);
        gs.setCharIntelligence(intelligence);
        gs.setTabMapPicLink(mapPicLink);
        return gameStateRepo.save(gs);
    }

    protected PlotButton createButton(
            Long screenId,
            String name,
            String type,
            String action,
            String description
    ) {
        PlotButton btn = new PlotButton();
        btn.setCreated(LocalDateTime.now());
        btn.setLastUpdateDate(LocalDateTime.now());
        btn.setVersion(0);
        btn.setScreenId(screenRepo.findById(screenId).get().getId());
        btn.setName(name);
        btn.setType(type);
        btn.setActions(action);
        btn.setDescription(description);

        return plotButtonRepo.save(btn);
    }

    protected ButtonAction createButtonAction(
            String description,
            String type,
            String ifCode,
            String doCode,
            String elseCode
    ) {
        ButtonAction btnAction = new ButtonAction();
        btnAction.setCreated(LocalDateTime.now());
        btnAction.setLastUpdateDate(LocalDateTime.now());
        btnAction.setVersion(0);
        btnAction.setDescription(description);
        btnAction.setType(type);
        btnAction.setIfCode(ifCode);
        btnAction.setDoCode(doCode);
        btnAction.setElseCode(elseCode);

        return buttonActionRepo.save(btnAction);
    }

    protected List<ButtonAction> getAllActions() {
        return StreamSupport.stream(buttonActionRepo.findAll().spliterator(), false).toList();
    }

    protected List<ButtonAction> getSimpleActions() {
        return StreamSupport.stream(buttonActionRepo.selectSimpleActions().spliterator(), false).toList();
    }
}
