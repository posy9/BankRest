package com.example.bankcards.controller;

import com.example.bankcards.dto.carddtos.*;
import com.example.bankcards.dto.transferdtos.TransferDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.specification.CardSpecificationBuilder;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController extends AbstractController<Card, CardReadDto, CardCreateDto, CardUpdateDto, CardFilterDto> {

    private final CardService cardService;

    public CardController(ModelMapper modelMapper, CardService service, CardSpecificationBuilder cardSpecificationBuilder) {
        super(modelMapper, service, Card.class, CardReadDto.class, cardSpecificationBuilder);
        this.cardService = service;
    }

    @PatchMapping("/{id}")
    public CardReadDto changeCardStatus(@PathVariable long id, @Valid @RequestBody CardPatchDto cardPatchDto) {
        Card card = cardService.changeCardStatus(id, modelMapper.map(cardPatchDto, Card.class).getStatus());
        Card changedCard = cardService.findById(id);
        return modelMapper.map(changedCard, CardReadDto.class);
    }

    @PostMapping("/{fromCardId}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public void makeTransfer(@PathVariable long fromCardId, @Valid @RequestBody TransferDto transferDto) {
        cardService.doTransfer(fromCardId, transferDto.getToCardId(), transferDto.getAmount());
    }
}
