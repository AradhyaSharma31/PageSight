package pagesight.pagesight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import pagesight.pagesight.model.ElementSelector;

public interface ElementSelectorRepository extends JpaRepository<ElementSelector, Long> {

    List<ElementSelector> findByMonitorId(Long monitorId);

    void deleteByMonitorId(Long monitorId);
}
