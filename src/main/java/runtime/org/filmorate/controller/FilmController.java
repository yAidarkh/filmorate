package runtime.org.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runtime.org.filmorate.model.Film;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    @PostMapping()
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film.addFilm(film);
        log.debug("Добавлен новый фильм с id: {}", film.getId());
        return ResponseEntity.ok(film);
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        if (!Film.films.containsKey(film.getId())) {
            log.info("Неверный id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }
        Film.addFilm(film);
        log.debug("Фильм с id: {} обновлен", film.getId());
        return ResponseEntity.ok(film);
    }


    @GetMapping()
    public Collection<Film> getFilms() {
        return Film.films.values();
    }
}
