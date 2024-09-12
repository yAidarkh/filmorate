package runtime.org.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runtime.org.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    public final HashMap<Long, User> users = new HashMap<>();
    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        addUser(user);
        log.debug("Добавлен новый пользватель с id: {}", user.getId());
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {

        if (!users.containsKey(user.getId())) {
            log.info("Неверный id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }
        addUser(user);
        log.debug("Пользователь с id: {} обновлен", user.getId());
        return ResponseEntity.ok(user);
    }


    @GetMapping()
    public Collection<User> getUsers() {
        return users.values();
    }

    public void addUser(User user) {
        log.trace("Запущен метод добавления пользователя в память.");
        users.put(user.getId(), user);
    }
}
