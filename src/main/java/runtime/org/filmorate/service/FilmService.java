package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.exceptions.FilmNotFoundException;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.storage.FilmStorage;
import runtime.org.filmorate.storage.UserStorage;

import java.util.*;

@Service
@Getter
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film putLike(Long filmId, Long userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        Film film = getById(filmId);
        film.getLikes().add(userId);;
        return film;
    }

    public Film removeLike(Long filmId, Long userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        Film film = getById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> showPopularFilms(int count) {
        if (count > filmStorage.getFilms().size()) {
            count = filmStorage.getFilms().size();
        }
        List<Film> films = filmStorage.getFilms()
                .values()
                .stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size()))
                .toList().reversed();

        return films.subList(0,count);
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film removeFilm(Film film) {
        return filmStorage.removeFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms().values();
    }

    public Film getById(Long id) {
        return filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Film with {"+id+"} not found"));
    }
}
