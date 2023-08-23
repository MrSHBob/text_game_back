package game.textgameserver.controller;

import game.textgameserver.config.JwtUtils;
import game.textgameserver.dao.User;
import game.textgameserver.model.AuthenticationRequest;
import game.textgameserver.service.UserService;
import game.textgameserver.utility.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest req
    ) {
        UserDetails user = null;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            user = userService.findByName(req.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.toString());
        }
        if (user != null) {
            return ResponseEntity
                    .ok()
                    .body(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(
            @RequestBody AuthenticationRequest req
    ) {
        User user = null;
        try {
            user = userService.create(
                req.getEmail(), req.getPassword(), "user"
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.toString());
        }

        if ((user != null) && (user.getId() > 0)) {
            user.setPassword(null);
            String jsonResponse = JsonSerializer.gson().toJson(user);

            return ResponseEntity
                    .ok()
                    .body(jsonResponse);
        }
        return ResponseEntity.status(400).body("Registration failed");
    }

    //TODO logoff (maybe) GET, not need, default available

}
