package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.User;
import runtime.org.filmorate.dao.UserStorageDao;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {
    private final UserStorageDao userStorage;
    private final JdbcTemplate jdbcTemplate;

    public User addFriend(Long userId, Long friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public User removeFriend(Long userId, Long otherId) {
        return userStorage.removeFriend(userId, otherId);
    }

    public Set<User> getMutualFriends(Long otherId, Long userId) {
        return userStorage.getMutualFriends(otherId, userId);
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User removeUser(User user) {
        return userStorage.removeUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public Set<User> getUserFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public User getUserById(Long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

}
