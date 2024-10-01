package runtime.org.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import runtime.org.filmorate.validators.ReleaseDate;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class Film {

    private static long idCounter = 1;

    private long id;
    @NotBlank(message = "Название не может быть пустым.")
    private String name;
    @Size(min = 1, max = 200, message = "Максимальное количество символов 200, минимальное 1.")
    private String description;
    @NotNull
    @ReleaseDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной.")
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.id = generateId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    private long generateId() {
        return idCounter++;
    }

}
