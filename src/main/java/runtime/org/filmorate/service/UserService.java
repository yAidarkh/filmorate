package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.User;
import runtime.org.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {
    private final UserStorage userStorage;

    public User addFriend(Long otherId, Long userId) {
        User user = getUserById(userId);
        User other = getUserById(otherId);
        user.getFriends().add(other.getId());
        other.getFriends().add(user.getId());
        return user;
    }

    public User removeFriend(Long otherId, Long userId) {
        User user = getUserById(userId);
        User other = getUserById(otherId);
        user.getFriends().remove(other.getId());
        other.getFriends().remove(user.getId());
        return user;
    }

    public Set<User> getMutualFriends(Long otherId, Long userId) {
        User user = getUserById(userId);
        User other = getUserById(otherId);
        Set<Long> mutualFriends = new HashSet<>(other.getFriends());
        mutualFriends.remove(user.getId());
        Set<User> commonFriends = new HashSet<>();
        for (Long mutualFriend : mutualFriends) {
            commonFriends.add(getUserById(mutualFriend));
        }
        return commonFriends;
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
        return userStorage.getUsers().values();
    }

    public Set<User> getUserFriends(Long userId) {
        Set<User> friends = new HashSet<>();
        for (Long friend : getUserById(userId).getFriends()) {
            friends.add(getUserById(friend));
        }
        return friends;
    }

    public User getUserById(Long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

}
