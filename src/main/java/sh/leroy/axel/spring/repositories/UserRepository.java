package sh.leroy.axel.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import sh.leroy.axel.spring.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOne(long id);
    User findByEmailIgnoreCase(String email);
    User findByUsernameIgnoreCase(String username);
    User findByEmailOrUsernameIgnoreCase(String email, String username);
}
