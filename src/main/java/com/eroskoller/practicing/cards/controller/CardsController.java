package com.eroskoller.practicing.cards.controller;


import com.eroskoller.practicing.cards.constants.CardsConstants;
import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.dto.CardsContactInfoDto;
import com.eroskoller.practicing.cards.dto.ErrorResponseDto;
import com.eroskoller.practicing.cards.dto.ResponseDto;
import com.eroskoller.practicing.cards.entity.CardEntity;
import com.eroskoller.practicing.cards.exception.CardAlreadyExistsException;
import com.eroskoller.practicing.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
//@AllArgsConstructor
@Validated
@Tag(
        name = "Cards",
        description = "The Cards API"
)
public class CardsController {

    private final CardService cardService;
    private final CardsContactInfoDto cardsContactInfoDto;
    private final Environment environment;
    @Value("${build.version}")
    private String buildVersion;

    public CardsController(CardService cardService, CardsContactInfoDto cardsContactInfoDto, Environment environment) {
        this.cardService = cardService;
        this.cardsContactInfoDto = cardsContactInfoDto;
        this.environment = environment;
    }

    @Operation(
            summary = "Create Card",
            description = "Create Card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestBody CardDto cardDto) {

        Optional<CardEntity> byMobileNumber = this.cardService.findByMobileNumber(cardDto.getMobileNumber());
        if (byMobileNumber.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + cardDto.getMobileNumber());
        }
        this.cardService.createCard(cardDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardDto> getCard(@Valid
                                           @RequestParam(required = true)
                                           @Pattern(regexp = "(^$|[0-9]{9,11})", message = "Mobile number must be between 9 and 11 digits")
                                           String mobileNumber) {
        return ResponseEntity.ok(this.cardService.getCard(mobileNumber));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = this.cardService.updateCard(cardDto);
        if (isUpdated) {
            this.cardService.updateCard(cardDto);
            return ResponseEntity.ok(new ResponseDto("200", "Card updated successfully"));
        } else {
            return ResponseEntity.ok(new ResponseDto("417", "Card not updated"));
        }

    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delteCard(@Valid
                                                 @RequestParam(required = true)
                                                 @Pattern(regexp = "(^$|[0-9]{9,11})", message = "Mobile number must be between 9 and 11 digits")
                                                 String mobileNumber) {
        boolean isDeleted = this.cardService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto("200", "Card deleted successfully"));
        } else {
            return ResponseEntity.ok(new ResponseDto("417", "Card not updated"));

        }
    }

    @Operation(
            summary = "Get Build Version REST API",
            description = "REST API to get build version"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.ok(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}

