package com.example.bankcards.controller;

import com.example.bankcards.dto.carddtos.*;
import com.example.bankcards.dto.transferdtos.TransferDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.security.service.SecurityService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.specification.CardSpecificationBuilder;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController extends AbstractController<Card, CardReadDto, CardCreateDto, CardUpdateDto, CardFilterDto> {

    private final CardService cardService;
    private final SecurityService securityService;

    public CardController(ModelMapper modelMapper, CardService service,
                          CardSpecificationBuilder cardSpecificationBuilder,
                          SecurityService securityService) {
        super(modelMapper, service, Card.class, CardReadDto.class, cardSpecificationBuilder);
        this.cardService = service;
        this.securityService = securityService;
    }

    @Override
    @GetMapping
    public Page<CardReadDto> getAllEntities(@ModelAttribute CardFilterDto filterDto,
                                            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        if (securityService.hasRole("ROLE_USER")) {
            filterDto.setUser_id(securityService.getUserId());
        }
        ;
        return super.getAllEntities(filterDto, pageable);
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
