package com.eroskoller.practicing.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{9,11})",message = "Mobile number must be between 9 and 11 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "4365327698"
    )
    private String mobileNumber;
    @PositiveOrZero
    private Integer totalLimit;
    @PositiveOrZero
    private Integer amountUsed;
    @PositiveOrZero
    private Integer availableAmount;

}
