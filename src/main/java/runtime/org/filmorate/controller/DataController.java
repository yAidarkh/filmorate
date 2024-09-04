package runtime.org.filmorate.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.model.User;

import java.util.HashMap;

@Getter
@Setter
@Slf4j
public class DataController {
    private final HashMap<Long, User> users = new HashMap<>();
    private final HashMap<Long, Film> films = new HashMap<>();

    private static DataController instance = null;

    private DataController() {
    }

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    public void addUser(User user) {
        log.trace("Запущен метод добавления пользователя в память.");
        users.put(user.getId(),user);
    }

    public void addFilm(Film film) {
        log.trace("Запущен метод добавления фильма в память.");
        films.put(film.getId(),film);
    }

    public void clearData(){
        users.clear();
        films.clear();
    }
}
