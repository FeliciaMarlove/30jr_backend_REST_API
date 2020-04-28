package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.PK_User_Path;
import be.iramps.florencemary._30jd_back.models.UserPath;
import org.springframework.data.repository.CrudRepository;

public interface UserPathRepository extends CrudRepository<UserPath, PK_User_Path> {
}
