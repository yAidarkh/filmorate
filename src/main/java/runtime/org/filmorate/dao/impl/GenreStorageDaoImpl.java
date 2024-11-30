package runtime.org.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.dao.GenreStorageDao;
import runtime.org.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreStorageDaoImpl implements GenreStorageDao {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Genre> findById(long id) {
        return jdbcTemplate.query("select * from genres where id = ?", this::mapRow, id).stream().findFirst();
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("select * from genres", this::mapRow);
    }

    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
