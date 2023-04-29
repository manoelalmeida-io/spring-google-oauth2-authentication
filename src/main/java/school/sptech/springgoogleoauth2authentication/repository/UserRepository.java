package school.sptech.springgoogleoauth2authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.springgoogleoauth2authentication.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findBySub(String sub);
}
