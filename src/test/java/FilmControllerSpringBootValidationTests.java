import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import runtime.org.filmorate.controller.DataController;
import runtime.org.filmorate.controller.FilmController;
import runtime.org.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class FilmControllerSpringBootValidationTests {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    FilmController filmController;

    @BeforeEach
    void init(){
        filmController = new FilmController();
    }

    @AfterEach
    void clearData(){
        DataController.getInstance().clearData();
    }

    @Test
    public void createFilmSucces(){
        LocalDate localDate = LocalDate.of(1998,12,8);
        Film film = new Film("some name","smthg",localDate,200);

        filmController.createFilm(film);

        assertEquals(1, DataController.getInstance().getFilms().size());
    }

    @Test
    public void createFilmFailName(){
        LocalDate localDate = LocalDate.of(1998,12,8);
        Film film = new Film("","smthg",localDate,200);

        String expected = "Название не может быть пустым.";
        String actual = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected,actual);
    }

    @Test
    public void createFilmFailDescription(){
        String description = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        LocalDate localDate = LocalDate.of(1998,12,8);
        Film film = new Film("asd",description,localDate,200);

        String expected = "Максимальное количество символов 200, минимальное 1.";
        String actual = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected,actual);
    }

    @Test
    public void createFilmFailReleaseDate(){
        LocalDate localDate = LocalDate.of(1894,12,8);
        Film film = new Film("asd","asd",localDate,200);

        String expected = "Дата релиза не может быть раньше 28 декабря 1895 года.";
        String actual = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected,actual);
    }

    @Test
    public void createFilmFailDuration(){
        LocalDate localDate = LocalDate.of(1897,12,8);
        Film film = new Film("asd","asd",localDate,-1);

        String expected = "Продолжительность не может быть отрицательной.";
        String actual = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected,actual);
    }

    protected String validateAndGetFirstMessageTemplate(Film obj){
        return validator.validate(obj).stream().
                findFirst().
                orElseThrow().
                getConstraintDescriptor().
                getMessageTemplate();
    }
}
