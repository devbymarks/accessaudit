package com.accessaudit.repo;
import com.accessaudit.model.AuditEvent; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface AuditEventRepository extends JpaRepository<AuditEvent,Long> {
 List<AuditEvent> findTop200ByOrderByOccurredAtDesc(); List<AuditEvent> findByRequestIdOrderByOccurredAtDesc(Long requestId);
}
