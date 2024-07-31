package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.ServerLog;

import java.util.Optional;

public interface ServerLogRepository extends JpaRepository<ServerLog, Long> {

    Optional<ServerLog> findServerLogByKeyValue(String key);
    Optional<ServerLog> findByKeyValue(String key);
}
