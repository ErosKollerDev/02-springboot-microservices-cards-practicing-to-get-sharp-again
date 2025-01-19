package com.eroskoller.practicing.cards.service;


import com.eroskoller.practicing.cards.constants.CardsConstants;
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
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {


    private final CardRepository cardRepository;


    public void createCard(String mobileNumber) {
        Optional<CardEntity> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    public Optional<CardEntity> findByMobileNumber(String mobileNumber) {
        return cardRepository.findByMobileNumber(mobileNumber);
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private CardEntity createNewCard(String mobileNumber) {
        CardEntity newCard = new CardEntity();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setCardStatus("ACTIVE");
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }


    public boolean updateCard(@Valid CardDto cardDto) {
        CardEntity cardFoundEntity = this.cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(() -> new CardNotFoundException("Card not found"));
        CardMapper.mapToCardEntity(cardDto, cardFoundEntity);
        this.cardRepository.save(cardFoundEntity);
        return true;
    }

    public CardDto getCard(String mobileNumber) {
        CardEntity cardFound = this.cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new CardNotFoundException("Card not found"));
        return CardMapper.mapToCardDto(cardFound, new CardDto());
    }

    public boolean deleteCard(String mobileNumber) {
        CardEntity cardFound = this.cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new CardNotFoundException("Card not found"));
        cardRepository.deleteById(cardFound.getCardId());
        return  true;
    }
}
