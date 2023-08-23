package game.textgameserver.controller;

import game.textgameserver.dao.GameState;
import game.textgameserver.dao.User;
import game.textgameserver.model.GameRequest;
import game.textgameserver.model.GameResponse;
import game.textgameserver.model.MoveRequest;
import game.textgameserver.service.GameService;
import game.textgameserver.service.GameStateMessageStorage;
import game.textgameserver.service.MoveService;
import game.textgameserver.service.UserService;
import game.textgameserver.utility.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final MoveService moveService;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userService.findUserByName(username);
    }

    @PostMapping("/game/loadSave")
    public ResponseEntity<String> loadSavedGame() {
        GameState gs = null;
        try {
            gs = gameService.getGameStateByOwnerId(getCurrentUser().getId() );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.toString());
        }

        if ((gs != null) && (gs.getId() > 0)) {
            String jsonResponse = JsonSerializer.gson().toJson(gs);

            return ResponseEntity
                    .ok()
                    .body(jsonResponse);
        }
        return ResponseEntity.status(400).body("Game loading failed");
    }

    @PostMapping("/game/loadNew")
    public ResponseEntity<String> loadNewGame() {
        GameState gs = null;
        try {
            gs = gameService.newGameStateForOwner(getCurrentUser());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.toString());
        }

        if ((gs != null) && (gs.getId() > 0)) {
            String jsonResponse = JsonSerializer.gson().toJson(gs);

            return ResponseEntity
                    .ok()
                    .body(jsonResponse);
        }
        return ResponseEntity.status(400).body("New game loading failed");
    }

    @PostMapping("/game/makingMove")
    public ResponseEntity<String> MakingMove(
            @RequestBody MoveRequest req
    ) {
        GameState gs = null;
        try {
            gs = moveService.makingMove(req.getId(), getCurrentUser());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.toString());
        }

        String msg = GameStateMessageStorage.takeMsg(gs.getId());

        if ((gs != null) && (gs.getId() > 0)) {
            GameResponse gResp = new GameResponse();
            gResp.setGameState(gs);
            if (msg != null) gResp.setMessage(msg);

            String jsonResponse = JsonSerializer.gson().toJson(gResp);

//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.set("Access-Control-Allow-Origin", "http://localhost:3000");

            return ResponseEntity
                    .ok()
//                    .headers(responseHeaders)
                    .body(jsonResponse);
        }
        return ResponseEntity.status(400).body("Making action failed");
    }
}
