package runtime.org.filmorate.dao;

import runtime.org.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorageDao {
    Film createFilm(Film film);

    Film removeFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Optional<Film> findById(Long id);

    Film putLike(Long filmId, Long userId);

    Film removeLike(Long filmId, Long userId);

    List<Film> showPopularFilms(Integer count);
}
