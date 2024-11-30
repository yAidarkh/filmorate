package runtime.org.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import runtime.org.filmorate.dao.MpaStorageDao;
import runtime.org.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaStorageDaoImpl implements MpaStorageDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> findById(long id) {
        return jdbcTemplate.query("select * from mpa where id = ?", this::mapRow, id).stream().findFirst();
    }

    @Override
    public List<Mpa> findAll() {
        return jdbcTemplate.query("select * from mpa", this::mapRow);
    }

    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
