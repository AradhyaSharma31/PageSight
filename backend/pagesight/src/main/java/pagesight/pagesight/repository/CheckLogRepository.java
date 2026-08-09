package pagesight.pagesight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import pagesight.pagesight.model.CheckLog;

public interface CheckLogRepository extends JpaRepository<CheckLog, Long> {

    List<CheckLog> findByMonitorIdOrderByCheckedAtDesc(Long monitorId);

    long countByMonitorId(Long monitorId);

}
