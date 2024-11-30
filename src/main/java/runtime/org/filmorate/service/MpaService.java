package runtime.org.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runtime.org.filmorate.dao.MpaStorageDao;
import runtime.org.filmorate.exceptions.MpaNotFoundException;
import runtime.org.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorageDao storageDao;

    public Optional<Mpa> findById(long id) {
        Optional<Mpa> mpa = storageDao.findById(id);
        if (mpa.isEmpty()) {
            throw new MpaNotFoundException("Mpa with id " + id + " not found");
        }
        return mpa;
    }

    ;

    public List<Mpa> findAll() {
        return storageDao.findAll();
    }

    ;
}
