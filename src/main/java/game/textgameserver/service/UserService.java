package game.textgameserver.service;

import game.textgameserver.dao.User;
import game.textgameserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    public UserDetails findByName(String name) {
        User user = repo.findByUsername(name);
        if (user == null) throw new UsernameNotFoundException(name);
        UserDetails ud = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))

        );
        return ud;
    }

    public User findUserByName(String name) {
        User user = repo.findByUsername(name);
        return user;
    }

    public User create(String username, String password, String role) {
        User u = new User();
        u.setCreated(LocalDateTime.now());
        u.setLastUpdateDate(LocalDateTime.now());
        u.setVersion(0);
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        return repo.save(u);
    }

    public boolean delete(User user) {
        try {
            repo.delete(user);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }
}
