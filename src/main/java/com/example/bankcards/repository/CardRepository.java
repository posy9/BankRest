package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends EntityRepository<Card, Long> {

    @Modifying
    @Query("""
                UPDATE Card c
                SET c.status.id = 3
                WHERE c.expiryDate < CURRENT_DATE
                AND c.status.id != 3
            """)
    int updateExpiredCards();

}
