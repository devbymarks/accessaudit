package com.accessaudit.repo;
import com.accessaudit.model.DirectoryUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface DirectoryUserRepository extends JpaRepository<DirectoryUser,Long> { List<DirectoryUser> findAllByOrderByFullNameAsc(); boolean existsByEmailIgnoreCase(String email); boolean existsByEmailIgnoreCaseAndIdNot(String email,Long id); }
