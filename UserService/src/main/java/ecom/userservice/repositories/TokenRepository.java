package ecom.userservice.repositories;

import ecom.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String token);

    @Modifying
    @Transactional
    @Query(value = "UPDATE token SET is_active = false WHERE value = :token", nativeQuery = true)
    void disable(String token);

    Optional<Token> findByValueAndIsActiveAndExpiryAtGreaterThan(String token, Boolean isActive, Date date);
}