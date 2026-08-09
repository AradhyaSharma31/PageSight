package pagesight.pagesight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import pagesight.pagesight.model.Monitor;
import pagesight.pagesight.model.MonitorStatus;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    List<Monitor> findByUserId(Long userId);

    List<Monitor> findByUserIdAndFavoriteTrue(Long userId);

    List<Monitor> findByStatus(MonitorStatus status);

    long countByUserId(Long userId);
}
