package runtime.org.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
public class User {
    private static long idCounter = 1;

    private long id;
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Некоректно указана почта.")
    private String email;
    @NotBlank(message = "Login не может быть пустым.")
    private String login;
    private String name;
    @Past(message = "Некоректно указана дата рождения.")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = generateId();
        this.email = email;
        this.login = login;
        if (name == null || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    private long generateId() {
        return idCounter++;
    }
}
