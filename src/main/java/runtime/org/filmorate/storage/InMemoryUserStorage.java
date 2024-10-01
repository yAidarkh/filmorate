package runtime.org.filmorate.storage;


import lombok.Getter;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.exceptions.NullArgumentException;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.User;

import java.util.HashMap;
import java.util.Optional;

@Component
@Getter
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new NullArgumentException("user is null");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User removeUser(User user) {
        if (user == null) {
            throw new NullArgumentException("user is null");
        }
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("user not found");
        }
        return users.remove(user.getId());
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new NullArgumentException("user is null");
        }
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("user not found");
        }
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }



}
