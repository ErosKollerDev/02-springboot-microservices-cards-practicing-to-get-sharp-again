package com.eroskoller.practicing.cards.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {

    @NotEmpty
    private String cardNumber;
    @NotEmpty
    private String cardType;
    @NotEmpty
    private String cardStatus;
    @NotEmpty
    private String mobileNumber;
    @Positive
    private Integer totalLimit;
    @Positive
    private Integer amountUsed;
    @Positive
    private Integer availableAmount;

}
