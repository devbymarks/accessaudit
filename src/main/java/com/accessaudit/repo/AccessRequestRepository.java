package com.accessaudit.repo;
import com.accessaudit.model.AccessRequest; import org.springframework.data.jpa.repository.JpaRepository; import java.time.Instant; import java.util.List;
public interface AccessRequestRepository extends JpaRepository<AccessRequest,Long> {
 List<AccessRequest> findAllByOrderByCreatedAtDesc(); long countByStatus(String status); List<AccessRequest> findByStatusAndReviewDueAtBeforeOrderByReviewDueAtAsc(String status, Instant before);
}
