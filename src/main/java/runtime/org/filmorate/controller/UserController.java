package runtime.org.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import runtime.org.filmorate.model.User;
import runtime.org.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
        log.info("POST /users");
        return userService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        log.info("PUT /users");
        return userService.updateUser(user);
    }

    @DeleteMapping()
    public User deleteUser(@Valid @RequestBody User user) {
        log.info("DELETE /users");
        return userService.removeUser(user);
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("GET /users");
        return userService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendsId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendsId) {
        log.info("PUT /users/{id}/friends/{friendsId}");
        return userService.addFriend(friendsId, id);
    }

    @DeleteMapping("/{id}/friends/{friendsId}")
    public User removeFriend(@PathVariable Long id, @PathVariable Long friendsId) {
        log.info("DELETE /users/{id}/friends/{friendsId}");
        return userService.removeFriend(friendsId, id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable Long id) {
        log.info("GET /users/{id}/friends");
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getMutualFriends(otherId, id);
    }
}
