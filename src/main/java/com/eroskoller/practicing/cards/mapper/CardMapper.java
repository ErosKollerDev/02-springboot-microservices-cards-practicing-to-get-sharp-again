package com.eroskoller.practicing.cards.mapper;

import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.entity.CardEntity;

public class CardMapper {


    public static CardDto mapToCardDto(CardEntity card, CardDto cardDto) {
        return CardDto.builder()
                .cardNumber(card.getCardNumber())
                .cardStatus(card.getCardStatus())
                .cardType(card.getCardType())
                .build();
    }

    public static CardEntity mapToCardEntity(CardDto cardDto, CardEntity card) {
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardStatus(cardDto.getCardStatus());
        card.setCardType(cardDto.getCardType());
        return card;
    }

}
