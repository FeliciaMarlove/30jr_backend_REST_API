package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.PK_User_Path;
import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.models.UserPath;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPathRepository extends CrudRepository<UserPath, PK_User_Path> {
    Optional<UserPath> findByPkUserPath(PK_User_Path pk_user_path);
}
