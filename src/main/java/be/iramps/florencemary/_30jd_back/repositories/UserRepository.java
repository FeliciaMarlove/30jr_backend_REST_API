package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
