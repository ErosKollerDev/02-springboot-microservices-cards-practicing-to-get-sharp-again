package com.eroskoller.practicing.cards.service;


import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.entity.CardEntity;
import com.eroskoller.practicing.cards.mapper.CardMapper;
import com.eroskoller.practicing.cards.repository.CardRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {


    private final CardRepository cardRepository;


    public void saveCard(@Valid CardDto cardDto) {
        CardEntity cardEntity = CardMapper.mapToCardEntity(cardDto, new CardEntity());
        cardEntity.setCreatedBy("eros");
        cardEntity.setCreatedAt(LocalDateTime.now());
        this.cardRepository.save(cardEntity);
    }
}
