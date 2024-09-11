import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import runtime.org.filmorate.controller.UserController;
import runtime.org.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerSpringBootValidationTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    UserController userController;

    @BeforeEach
    void init() {
        userController = new UserController();
    }


    @Test
    public void createUserSuccess() {
        LocalDate localDate = LocalDate.of(1998, 12, 8);
        User user = new User("smthn@gmail.com", "smthg", "smthg", localDate);

        userController.createUser(user);

        assertEquals(1, User.users.size());
    }

    @Test
    public void createUserFailEmail() {
        LocalDate localDate = LocalDate.of(1998, 12, 8);
        User user = new User("smthggmail.com", "smthg", "smthg", localDate);

        String expected = "Некоректно указана почта.";
        String actual = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, actual);

    }

    @Test
    public void createUserFailLogin() {
        LocalDate localDate = LocalDate.of(1998, 12, 8);
        User user = new User("smthg@gmail.com", "", "smthg", localDate);

        String expected = "Login не может быть пустым.";
        String actual = validateAndGetFirstMessageTemplate(user);
        assertEquals(expected, actual);
    }

    @Test
    public void createUserFailBirthday() {
        LocalDate localDate = LocalDate.of(2024, 12, 8);
        User user = new User("smthg@gmail.com", "smthg", "smthg", localDate);

        String expected = "Некоректно указана дата рождения.";
        String actual = validateAndGetFirstMessageTemplate(user);
        assertEquals(expected, actual);
    }

    @Test
    public void createUserWithEmptyName() {
        LocalDate localDate = LocalDate.of(2024, 12, 8);
        User user = new User("smthg@gmail.com", "smthg", "", localDate);

        String expected = user.getLogin();
        String actual = user.getName();
        assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(User obj) {
        return validator.validate(obj).stream().
                findFirst().
                orElseThrow().
                getConstraintDescriptor().
                getMessageTemplate();
    }
}
