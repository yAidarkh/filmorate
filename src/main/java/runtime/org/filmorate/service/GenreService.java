package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.dao.GenreStorageDao;
import runtime.org.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorageDao genreStorageDao;

    public Optional<Genre> findById(long id) {
        return genreStorageDao.findById(id);
    }

    ;

    public List<Genre> findAll() {
        return genreStorageDao.findAll();
    }

    ;
}
