package pagesight.pagesight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import pagesight.pagesight.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
