package runtime.org.filmorate.dao;


import runtime.org.filmorate.model.User;


import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserStorageDao {
    User createUser(User user);

    User removeUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    Optional<User> findById(long id);

    User addFriend(long userId, long friendId);

    User removeFriend(long userId, long friendId);

    Set<User> getMutualFriends(long userId, long otherId);

    Set<User> getFriends(long userId);
}
