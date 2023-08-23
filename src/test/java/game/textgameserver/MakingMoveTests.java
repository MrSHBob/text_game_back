package game.textgameserver;

import game.textgameserver.dao.*;
import game.textgameserver.repo.*;
import game.textgameserver.service.GameStateMessageStorage;
import game.textgameserver.service.MoveService;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TextGameServerApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MakingMoveTests {
    StringBuilder errors = null;

    @Autowired
    private MoveService moveService;
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

    void before() {
        errors = new StringBuilder();
    }
    void after() {
        if ((errors != null) && (!errors.isEmpty())) {
            Assert.assertEquals("", errors.toString());
        }
    }

    @Test
    @Order(1)
    public void makingMove() {
        before();

        User user = userRepo.findByUsername("usr11");
        PlotButton pushedButton = plotButtonRepo.findById(16L).get();
        Screen screen = screenRepo.findById(pushedButton.getScreenId()).get();

        // prepare state
        GameState gs = createGameState(
                user,
                screen,
                "Human",
                "Priest",
                11,
                11,
                11
        );

        GameState gs1 = moveService.makingMove(
                pushedButton.getId(),
                user
        );

        GameState gs2 = gameStateRepo.findByUser(user);

        String msg = GameStateMessageStorage.takeMsg(gs.getId());

        after();
        System.out.println();
    }








    private GameState createGameState(
            User user,
            Screen screen,
            String race,
            String theClass,
            Integer strength,
            Integer agility,
            Integer endurance
    ) {
        GameState existGS = gameStateRepo.findByUser(user);
        if (existGS != null) {
            gameStateRepo.delete(gameStateRepo.findByUser(user));
        }
        existGS = gameStateRepo.findByUser(user);
        if (existGS != null) {
            throw new RuntimeException("Garbage game states.");
        }


        GameState gs = new GameState();
        gs.setCreated(LocalDateTime.now());
        gs.setLastUpdateDate(LocalDateTime.now());
        gs.setVersion(0);
        gs.setUser(user);
        gs.setActiveScreen(screen);
        gs.setCharRace(race);
        gs.setCharClass(theClass);
        gs.setCharStrength(strength);
        gs.setCharAgility(agility);
        gs.setCharEndurance(endurance);
        return gameStateRepo.save(gs);
    }
}
