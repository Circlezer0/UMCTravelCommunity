package travel.travel_community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.travel_community.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserid(String userid);

    @Query(nativeQuery = true, value = "SELECT * FROM users ORDER BY RAND()")
    List<User> findRandomUsers(Pageable pageable);

    @Query("SELECT us FROM User us WHERE us.createdDate >= :startDate")
    List<User> findRecentSignupUsers(@Param("startDate") LocalDateTime startDate);
}
