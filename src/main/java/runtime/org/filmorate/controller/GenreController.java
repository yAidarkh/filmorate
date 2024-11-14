package runtime.org.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runtime.org.filmorate.model.Genre;
import runtime.org.filmorate.service.GenreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> findAll() {
        log.info("GET /genres");
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Genre> findById(@PathVariable long id) {
        log.info("GET /genres/{}", id);
        return genreService.findById(id);
    }
}
