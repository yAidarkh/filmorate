package runtime.org.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mpa {
    private long id;
    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    public Mpa(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
