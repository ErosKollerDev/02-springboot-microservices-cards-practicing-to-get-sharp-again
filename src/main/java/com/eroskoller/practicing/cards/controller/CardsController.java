package com.eroskoller.practicing.cards.controller;


import com.eroskoller.practicing.cards.constants.CardsConstants;
import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.dto.ResponseDto;
import com.eroskoller.practicing.cards.entity.CardEntity;
import com.eroskoller.practicing.cards.exception.CardAlreadyExistsException;
import com.eroskoller.practicing.cards.service.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class CardsController {

    private final CardService cardService;


    @GetMapping("/fetch")
    public ResponseEntity<CardDto> getCard(@RequestParam(required = true) String mobileNumber) {
        return ResponseEntity.ok(this.cardService.getCard(mobileNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam(required = true) String mobileNumber) {

        Optional<CardEntity> byMobileNumber = this.cardService.findByMobileNumber(mobileNumber);
        if (byMobileNumber.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        this.cardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
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
    public ResponseEntity<ResponseDto> delteCard(@RequestParam(required = true) String mobileNumber) {
        boolean isDeleted = this.cardService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto("200", "Card deleted successfully"));
        } else {
            return ResponseEntity.ok(new ResponseDto("417", "Card not updated"));

        }
    }

}

