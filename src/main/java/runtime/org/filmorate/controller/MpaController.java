package runtime.org.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runtime.org.filmorate.model.Mpa;
import runtime.org.filmorate.service.MpaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> findAll() {
        log.info("GET /mpa");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> findById(@PathVariable long id) {
        log.info("GET /mpa/{}", id);
        return mpaService.findById(id);
    }
}
