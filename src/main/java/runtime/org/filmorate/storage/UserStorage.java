package runtime.org.filmorate.storage;


import runtime.org.filmorate.model.User;

import java.util.HashMap;
import java.util.Optional;


public interface UserStorage {
    User createUser(User user);
    User removeUser(User user);
    User updateUser(User user);
    HashMap<Long, User> getUsers();
    Optional<User> findById(long id);
}
