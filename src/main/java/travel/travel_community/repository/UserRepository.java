package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserid(String userid);
}
