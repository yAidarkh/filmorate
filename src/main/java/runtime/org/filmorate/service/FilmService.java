package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.exceptions.FilmNotFoundException;
import runtime.org.filmorate.exceptions.GenreIsntCorrectException;
import runtime.org.filmorate.exceptions.MpaIsntCorrectException;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.dao.FilmStorageDao;
import runtime.org.filmorate.dao.UserStorageDao;
import runtime.org.filmorate.model.Genre;

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
        if (count > filmStorage.getFilms().size()) {
            count = filmStorage.getFilms().size();
        }
        return filmStorage.showPopularFilms(count);
    }

    public Film createFilm(Film film) {
        List<Genre> genres = film.getGenres();
        for (Genre genre : genres) {
            if (genre.getId() > 6 || genre.getId() < 0) {
                throw new GenreIsntCorrectException("genre not correct");
            }
        }
        long mpaId = film.getMpa().getId();
        if (mpaId > 5 || mpaId < 0) {
            throw new MpaIsntCorrectException("Mpa not correct");
        }
        return filmStorage.createFilm(film);
    }

    public Film removeFilm(Film film) {
        return filmStorage.removeFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.findById(film.getId()).isEmpty()) {
            throw new UserNotFoundException("Film with id " + film.getId() + " not found");
        }
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film findFilmById(Long id) {
        Film film = filmStorage.findById(id).orElseThrow();
        if (film == null) {
            throw new FilmNotFoundException("film with id " + id + " not found");
        }
        return film;
    }
}
