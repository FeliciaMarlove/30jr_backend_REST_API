package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.Path;
import org.springframework.data.repository.CrudRepository;

/**
 * DAO parcours
 */
public interface PathRepository extends CrudRepository<Path, Integer> {
    /**
     * Retrouve un parcours sur base d'un nom de parcours
     * @param pathName String le nom du parcours recherché
     * @return Path le parcours trouvé
     */
    Path findByPathName(String pathName);
}
