package br.com.lucasvicente.contabancaria.repository;

import br.com.lucasvicente.contabancaria.entites.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByAccountNumber(Integer accountNumber);

    @Modifying
    @Transactional
    @Query(
            "UPDATE Account " +
                    "SET balance = balance + :value " +
                    "WHERE id = :accountId"
    )
    void deposit(@Param("accountId") Long accountId,@Param("value") BigDecimal value);

    @Modifying
    @Transactional
    @Query(
            "UPDATE Account " +
                    "SET balance = balance - :value " +
                    "WHERE id = :accountId"
    )
    void withdraw(@Param("accountId") Long accountId, @Param("value") BigDecimal value);

    @Query(
            "SELECT a.id " +
                    "FROM Account a " +
                    "JOIN a.pixKeys p " +
                    "WHERE p.keyValue = :pixKey"
    )
    Long findAccountByPixKey(@Param("pixKey") String pixKey);
}
