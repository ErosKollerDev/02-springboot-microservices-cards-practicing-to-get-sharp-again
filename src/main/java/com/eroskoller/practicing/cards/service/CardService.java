package com.eroskoller.practicing.cards.service;


import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.entity.CardEntity;
import com.eroskoller.practicing.cards.exception.CardAlreadyExistsException;
import com.eroskoller.practicing.cards.exception.CardNotFoundException;
import com.eroskoller.practicing.cards.mapper.CardMapper;
import com.eroskoller.practicing.cards.repository.CardRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {


    private final CardRepository cardRepository;


    public void saveCard(@Valid CardDto cardDto) {
        boolean exists = existsCard(cardDto.getCardNumber());
        if (exists) {
            throw new CardAlreadyExistsException("Card already exists");
        }
        CardEntity cardEntity = CardMapper.mapToCardEntity(cardDto, new CardEntity());
        this.cardRepository.save(cardEntity);
    }


    public boolean existsCard(String cardNumber) {
        Optional<CardEntity> byCardNumber = this.cardRepository.findByCardNumber(cardNumber);
        return byCardNumber.isPresent();
    }

    public boolean updateCard(@Valid CardDto cardDto) {
        boolean isUpdated = false;
        Optional<CardEntity> byCardNumber = this.cardRepository.findByCardNumber(cardDto.getCardNumber());
        if (byCardNumber.isPresent()) {
            CardEntity cardEntity = CardMapper.mapToCardEntity(cardDto, byCardNumber.get());
//            cardEntity.setUpdatedBy("eros");
//            cardEntity.setUpdatedAt(LocalDateTime.now());
            this.cardRepository.save(cardEntity);
            isUpdated = true;
        }
        return isUpdated;
    }

    public CardDto getCard(String cardNumber) {

        Optional<CardEntity> byCardNumber = this.cardRepository.findByCardNumber(cardNumber);
        if (byCardNumber.isPresent()) {
            return CardMapper.mapToCardDto(byCardNumber.get(), new CardDto());
        } else {
            throw new CardNotFoundException("Card not found");
        }

    }

    public boolean deleteCard(String cardNumber) {
        Optional<CardEntity> byCardNumber = this.cardRepository.findByCardNumber(cardNumber);
        if (byCardNumber.isPresent()) {
            this.cardRepository.deleteById(byCardNumber.get().getCardId());
            return true;
        } else {
            return false;
        }
    }
}
