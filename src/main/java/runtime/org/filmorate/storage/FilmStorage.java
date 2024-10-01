package runtime.org.filmorate.storage;

import runtime.org.filmorate.model.Film;

import java.util.HashMap;
import java.util.Optional;

public interface FilmStorage {
    public Film createFilm(Film film);
    public Film removeFilm(Film film);
    public Film updateFilm(Film film);
    HashMap<Long,Film> getFilms();
    Optional<Film> findById(long id);
}
