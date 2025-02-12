package runtime.org.filmorate.dao.impl;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.dao.UserStorageDao;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class UserStorageDaoImpl implements UserStorageDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).
                withTableName("users").
                usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()

        );
        int id = insert.executeAndReturnKey(map).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User removeUser(User user) {
        jdbcTemplate.update("delete from users where id = ?", user.getId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        findById(user.getId());
        jdbcTemplate.update("update users set " +
                        "email = ?, " +
                        "login = ?, " +
                        "name = ?, " +
                        "birthday =? " +
                        "where id = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from users", this::mapRow);
    }

    public Optional<User> findById(long id) {
        return jdbcTemplate.query("select * from users where id = ?", this::mapRow, id).stream().findFirst();
    }

    @Override
    public User addFriend(long userId, long friendId) {
        checkUsers(friendId);
        User user = findById(userId).get();
        String sql = "insert into friends (user_id, friend_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
        user.getFriends().add(friendId);
        return user;
    }

    @Override
    public User removeFriend(long userId, long friendId) {
        checkUsers(friendId);
        checkUsers(userId);
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
        return findById(userId).get();
    }

    @Override
    public Set<User> getMutualFriends(long userId, long otherId) {
        String sql = "select u.id, u.email, u.login, u.name, u.birthday " +
                "from users u " +
                "join friends f1 ON u.id = f1.friend_id " +
                "join friends f2 ON u.id = f2.friend_id " +
                "where f1.user_id = ? " +
                "and f2.user_id = ?";
        Collection<User> users = jdbcTemplate.query(sql, this::mapRow, userId, otherId);
        return new HashSet<>(users);
    }

    @Override
    public Set<User> getFriends(long userId) {
        checkUsers(userId);
        Set<User> friends = new HashSet<>();
        String sql = "select * from friends where user_id = ?";
        List<Long> friendsId = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> rs.getLong("friend_id"));
        friendsId.stream().map(friendId -> findById(friendId).get()).forEach(friends::add);
        return friends;
    }

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
        user.setId(rs.getLong("id"));
        return user;
    }

    protected void checkUsers(long id) {
        List<User> user = jdbcTemplate.query("select * from users where id = ?", this::mapRow, id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

}
