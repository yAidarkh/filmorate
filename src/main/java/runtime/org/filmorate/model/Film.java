package runtime.org.filmorate.model;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import runtime.org.filmorate.validators.ReleaseDate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class Film {
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
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
    private Set<Long> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }


}
