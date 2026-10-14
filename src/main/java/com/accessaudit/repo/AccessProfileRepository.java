package com.accessaudit.repo;
import com.accessaudit.model.AccessProfile; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface AccessProfileRepository extends JpaRepository<AccessProfile,Long> { List<AccessProfile> findAllByOrderBySystemNameAscNameAsc(); }
