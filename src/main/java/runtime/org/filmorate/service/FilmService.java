package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.dao.FilmStorageDao;
import runtime.org.filmorate.dao.UserStorageDao;

import java.util.*;

@Service
@Getter
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorageDao filmStorage;
    private final UserStorageDao userStorage;
    private final JdbcTemplate jdbcTemplate;

    public Film putLike(Long filmId, Long userId) {
        return filmStorage.putLike(filmId, userId);
    }

    public Film removeLike(Long filmId, Long userId) {
        return filmStorage.removeLike(filmId, userId);
    }

    public List<Film> showPopularFilms(int count) {
        return filmStorage.showPopularFilms(count);
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
        return filmStorage.getFilms();
    }

    public Film findFilmById(Long id) {
        return filmStorage.findById(id).orElseThrow();
    }
}
