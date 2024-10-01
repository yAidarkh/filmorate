package runtime.org.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("POST /films");
        return filmService.createFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT /films");
        return filmService.updateFilm(film);
    }

    @DeleteMapping()
    public Film removeFilm(@Valid @RequestBody Film film) {
        log.info("DELETE /films");
        return filmService.removeFilm(film);
    }

    @GetMapping()
    public Collection<Film> getFilms() {
        log.info("GET /films");
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        log.info("PUT /films/{id}/like/{userId}");
        return filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film unlikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        log.info("DELETE /films/{id}/like/{userId}");
        return filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam int count) {
        log.info("GET /popular");
        return filmService.showPopularFilms(count);
    }
}
