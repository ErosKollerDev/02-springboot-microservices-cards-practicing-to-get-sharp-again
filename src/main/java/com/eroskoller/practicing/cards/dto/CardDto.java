package com.eroskoller.practicing.cards.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {

    private String cardNumber;
    private String cardType;
    private String cardStatus;
    private String mobileNumber;
    private Integer totalLimit;
    private Integer amountUsed;
    private Integer availableAmount;

}
