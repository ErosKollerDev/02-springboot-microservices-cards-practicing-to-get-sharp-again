package com.eroskoller.practicing.cards.controller;


import com.eroskoller.practicing.cards.dto.CardDto;
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

    @PostMapping("/create")
    public ResponseEntity<CardDto>  createCard( @Valid @RequestBody CardDto cardDto){
        this.cardService.saveCard(cardDto);
        return ResponseEntity.ok(cardDto);
    }


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        log.info("Inside hello");
        return ResponseEntity.ok("hello");
    }
}

