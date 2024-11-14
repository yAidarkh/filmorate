package runtime.org.filmorate.dao;

import runtime.org.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorageDao {
    Optional<Genre> findById(long id);

    List<Genre> findAll();

}
