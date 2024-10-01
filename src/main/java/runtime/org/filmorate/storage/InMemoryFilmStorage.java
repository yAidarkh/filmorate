package runtime.org.filmorate.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.exceptions.FilmNotFoundException;
import runtime.org.filmorate.model.Film;

import java.util.HashMap;
import java.util.Optional;

@Component
@Getter
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Film createFilm(Film film) {
        if (film == null){
            throw new NullPointerException("film is null");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        if (film == null){
            throw new NullPointerException("film is null");
        }
        if (!films.containsKey(film.getId())){
            throw new FilmNotFoundException("film not found");
        }
        return films.remove(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        if (film == null){
            throw new NullPointerException("film is null");
        }
        if (!films.containsKey(film.getId())){
            throw new FilmNotFoundException("film not found");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        return Optional.ofNullable(films.get(id));
    }

}
