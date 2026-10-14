package com.accessaudit.repo;
import com.accessaudit.model.ManagedSystem; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ManagedSystemRepository extends JpaRepository<ManagedSystem,Long> { List<ManagedSystem> findAllByOrderByNameAsc(); boolean existsByCodeIgnoreCase(String code); boolean existsByCodeIgnoreCaseAndIdNot(String code,Long id); }
