package sdu.edu.kz.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.edu.kz.banking_app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
