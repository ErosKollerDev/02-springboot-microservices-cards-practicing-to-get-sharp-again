package com.eroskoller.practicing.cards.repository;


import com.eroskoller.practicing.cards.entity.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository <CardEntity, Long> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

    @Transactional
    @Modifying
    boolean deleteByCardNumber(String cardNumber);
}
