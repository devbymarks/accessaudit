package com.accessaudit.repo;
import com.accessaudit.model.AccessGrant; import org.springframework.data.jpa.repository.JpaRepository; import java.time.Instant; import java.util.List; import java.util.Optional;
public interface AccessGrantRepository extends JpaRepository<AccessGrant,Long> {
 List<AccessGrant> findAllByOrderByGrantedAtDesc(); long countByStatus(String status);
 List<AccessGrant> findByStatusAndReviewDueAtBeforeOrderByReviewDueAtAsc(String status, Instant before);
 Optional<AccessGrant> findFirstByEmailIgnoreCaseAndSystemNameAndProfileNameAndStatus(String email,String systemName,String profileName,String status);
}
