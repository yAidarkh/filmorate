package runtime.org.filmorate.dao.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.dao.FilmStorageDao;
import runtime.org.filmorate.model.Film;
import runtime.org.filmorate.model.Genre;
import runtime.org.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class FilmStorageDaoImpl implements FilmStorageDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT = """
            select films.id as film_id,
                   films.name as film_name,
                   films.description as film_description,
                   films.release_date as film_release_date,
                   films.duration as film_duration,
                   mpa.id as film_mpa_id,
                   mpa.name as film_mpa_name
            from films
                join mpa
                    on films.mpa_id = mpa.id
            """;

    @Override
    public Film createFilm(Film film) {
        List<Genre> genres = film.getGenres();
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).
                withTableName("films").
                usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa().getId()
        );
        long id = insert.executeAndReturnKey(map).longValue();
        String sql = "insert into films_genres (film_id,genre_id) values (?,?)";
        genres.forEach(genre -> jdbcTemplate.update(sql, id, genre.getId()));
        film = findById(id).orElseThrow();
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        jdbcTemplate.update("delete from films where id = ?", film.getId());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("update films set " +
                        "name = ?, " +
                        "description = ?, " +
                        "release_date = ?, " +
                        "duration =?, " +
                        "mpa_id = ? " +
                        "where id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Film putLike(Long filmId, Long userId) {
        String sql = "insert into likes (film_id, user_id) values (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
        return findById(filmId).orElseThrow();
    }

    @Override
    public Film removeLike(Long filmId, Long userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
        return findById(filmId).orElseThrow();
    }

    @Override
    public List<Film> showPopularFilms(Integer count) {
        String sql = SELECT + "LEFT JOIN likes ON likes.film_id = films.id " +
                "group by films.id, films.name, films.description, films.release_date, films.duration, mpa.id, mpa.name " +
                "order by count(likes.film_id) desc";
        List<Film> films = jdbcTemplate.query(sql, this::mapRow);
        return films.subList(0, count);
    }

    @Override
    public Optional<Film> findById(Long id) {
        String sql = SELECT + " where films.id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id).stream().findFirst();
    }

    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int mpaId = rs.getInt("film_mpa_id");
        String mpaName = rs.getString("film_mpa_name");
        Mpa mpa = new Mpa(mpaId, mpaName);
        Film film = new Film(
                rs.getString("film_name"),
                rs.getString("film_description"),
                rs.getDate("film_release_date").toLocalDate(),
                rs.getInt("film_duration"),
                mpa
        );
        long filmId = rs.getLong("id");
        film.setId(filmId);
        String sql = "select distinct genres.id, genres.name " +
                "from genres " +
                "join films_genres on films_genres.genre_id = genres.id " +
                "where films_genres.film_id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, this::mapRowGenre, filmId);
        film.getGenres().addAll(genres);
        return film;
    }

    public Genre mapRowGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
