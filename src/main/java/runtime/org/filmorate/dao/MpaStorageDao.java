package runtime.org.filmorate.dao;

import runtime.org.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorageDao {
    Optional<Mpa> findById(long id);

    List<Mpa> findAll();
}
