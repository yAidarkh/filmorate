package runtime.org.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runtime.org.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    public final HashMap<Long, Film> films = new HashMap<>();

    @PostMapping()
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        addFilm(film);
        log.debug("Добавлен новый фильм с id: {}", film.getId());
        return ResponseEntity.ok(film);
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.info("Неверный id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }
        addFilm(film);
        log.debug("Фильм с id: {} обновлен", film.getId());
        return ResponseEntity.ok(film);
    }


    @GetMapping()
    public Collection<Film> getFilms() {
        return films.values();
    }

    public void addFilm(Film film) {
        log.trace("Запущен метод добавления фильма в память.");
        films.put(film.getId(), film);
    }
}
