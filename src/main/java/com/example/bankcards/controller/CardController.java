package com.example.bankcards.controller;

import com.example.bankcards.dto.carddtos.CardCreateDto;
import com.example.bankcards.dto.carddtos.CardFilterDto;
import com.example.bankcards.dto.carddtos.CardReadDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.specification.CardSpecificationBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final ModelMapper modelMapper;
    private final CardService cardService;
    private final CardSpecificationBuilder specificationBuilder;

    @GetMapping
    Page<CardReadDto> getAllCards(@ModelAttribute CardFilterDto filterDto, Pageable pageable) {
        Specification<Card> cardSpecification = specificationBuilder.build(filterDto);
        Page<Card> cards = cardService.findMultiple(cardSpecification, pageable);
        return cards.map(card -> modelMapper.map(card, CardReadDto.class));
    }

    @GetMapping(value = "/{id}")
    CardReadDto getCardById(@PathVariable long id) {
        Card card = cardService.findById(id);
        return modelMapper.map(card, CardReadDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardReadDto createCard( @RequestBody @Valid CardCreateDto cardCreateDto) {
        Card card = modelMapper.map(cardCreateDto, Card.class);
        cardService.createCard(card);
        Card createdCard = cardService.findById(card.getId());
        return modelMapper.map(createdCard, CardReadDto.class);
    }

    @PutMapping(value = "/{id}")
    CardReadDto updateCard(@PathVariable long id, @Valid @RequestBody CardCreateDto entityCreateDto) {
        cardService.updateCard(id, modelMapper.map(entityCreateDto, Card.class));
        Card updatedCard = cardService.findById(id);
        return modelMapper.map(updatedCard, CardReadDto.class);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteEntity(@PathVariable long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
