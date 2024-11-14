package runtime.org.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Slf4j
public class User {
    private long id;
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Некоректно указана почта.")
    private String email;
    @NotBlank(message = "Login не может быть пустым.")
    private String login;
    private String name;
    @Past(message = "Некоректно указана дата рождения.")
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name == null || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

}
