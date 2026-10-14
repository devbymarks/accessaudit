package com.accessaudit.repo;
import com.accessaudit.model.AdminAccount; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface AdminAccountRepository extends JpaRepository<AdminAccount,Long> { Optional<AdminAccount> findByUsernameIgnoreCase(String username); boolean existsByUsernameIgnoreCase(String username); }
