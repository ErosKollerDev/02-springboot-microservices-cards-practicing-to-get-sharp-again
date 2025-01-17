package com.eroskoller.practicing.cards.controller;


import com.eroskoller.practicing.cards.dto.CardDto;
import com.eroskoller.practicing.cards.dto.ResponseDto;
import com.eroskoller.practicing.cards.exception.CardAlreadyExistsException;
import com.eroskoller.practicing.cards.service.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class CardsController {

    private final CardService cardService;


    @GetMapping("/fetch")
    public ResponseEntity<CardDto> getCard(@RequestParam(required = true) String cardNumber) {
        return ResponseEntity.ok(this.cardService.getCard(cardNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = this.cardService.updateCard(cardDto);
        if (isUpdated) {
            throw new CardAlreadyExistsException("Card already exists");
        }
        this.cardService.saveCard(cardDto);
        return ResponseEntity.ok(cardDto);
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
    public ResponseEntity<ResponseDto> delteCard(@RequestParam(required = true) String cardNumber) {
        boolean isDeleted = this.cardService.deleteCard(cardNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto("200", "Card deleted successfully"));
        } else {
            return ResponseEntity.ok(new ResponseDto("417", "Card not updated"));

        }
    }

}

