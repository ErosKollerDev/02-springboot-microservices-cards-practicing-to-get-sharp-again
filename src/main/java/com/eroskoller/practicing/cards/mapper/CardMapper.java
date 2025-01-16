package com.eroskoller.practicing.cards.mapper;

import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.entity.CardEntity;

public class CardMapper {


    public static CardDto mapToCardDto(CardEntity card, CardDto cardDto) {
        return CardDto.builder()
                .cardNumber(card.getCardNumber())
                .cardStatus(card.getCardStatus())
                .cardType(card.getCardType())
                .totalLimit(card.getTotalLimit())
                .amountUsed(card.getAmountUsed())
                .availableAmount(card.getAvailableAmount())
                .mobileNumber(card.getMobileNumber())
                .build();
    }

    public static CardEntity mapToCardEntity(CardDto cardDto, CardEntity card) {
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardStatus(cardDto.getCardStatus());
        card.setCardType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setMobileNumber(cardDto.getMobileNumber());
        return card;
    }

}
