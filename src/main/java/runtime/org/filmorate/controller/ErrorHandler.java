package runtime.org.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import runtime.org.filmorate.exceptions.FilmNotFoundException;
import runtime.org.filmorate.exceptions.NullArgumentException;
import runtime.org.filmorate.exceptions.UserNotFoundException;
import runtime.org.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNullArgumentException(final NullArgumentException e){
        return new ErrorResponse("", e.getMessage());
    };

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e){
        return new ErrorResponse("", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFoundException(final FilmNotFoundException e){
        return new ErrorResponse("", e.getMessage());
    }


}
