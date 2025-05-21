package com.example.bankcards.controller;

import com.example.bankcards.dto.carddtos.CardCreateDto;
import com.example.bankcards.dto.carddtos.CardFilterDto;
import com.example.bankcards.dto.carddtos.CardPatchDto;
import com.example.bankcards.dto.carddtos.CardReadDto;
import com.example.bankcards.dto.statusdtos.StatusReadDto;
import com.example.bankcards.dto.transferdtos.TransferDto;
import com.example.bankcards.dto.userdtos.UserReadDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.service.SecurityService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.specification.CardSpecificationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CardService cardService;

    @Mock
    private CardSpecificationBuilder specificationBuilder;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private CardController cardController;

    private Card testCard;
    private CardReadDto cardReadDto;
    private CardCreateDto cardCreateDto;
    private CardFilterDto cardFilterDto;
    private CardPatchDto cardPatchDto;

    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setId(1L);

        Status testStatus = new Status();
        testStatus.setId(2L);
        testStatus.setName("Active");

        testCard = new Card();
        testCard.setId(1L);
        testCard.setBalance(new BigDecimal("1000.00"));
        testCard.setStatus(testStatus);
        testCard.setCardNumber("4111111111111111");
        testCard.setExpiryDate(LocalDate.of(2026, 5, 31));
        testCard.setUser(testUser);

        cardReadDto = new CardReadDto();
        cardReadDto.setId(1L);
        cardReadDto.setBalance(new BigDecimal("1000.00"));

        cardCreateDto = new CardCreateDto();
        UserReadDto userReadDto = new UserReadDto();
        userReadDto.setId(1L);
        cardCreateDto.setUser(userReadDto);

        cardFilterDto = new CardFilterDto();

        cardPatchDto = new CardPatchDto();
        StatusReadDto newStatus = new StatusReadDto();
        newStatus.setId(3L);
        cardPatchDto.setStatus(newStatus);
    }


    @Test
    void createEntity_ShouldCallSuperCreateEntity_WhenNotAdmin() {

        when(securityService.isAdmin(1L)).thenReturn(false);
        when(modelMapper.map(any(CardCreateDto.class), eq(Card.class))).thenReturn(testCard);
        when(cardService.createEntity(any(Card.class))).thenReturn(testCard);
        when(modelMapper.map(nullable(Card.class), eq(CardReadDto.class))).thenReturn(cardReadDto);

        CardReadDto result = cardController.createEntity(cardCreateDto);

        assertEquals(cardReadDto, result);
    }

    @Test
    void getAllEntities_ShouldSetUserIdInFilterDto_WhenUserRole() {
        // Arrange
        long userId = 1L;
        when(securityService.hasRole("ROLE_USER")).thenReturn(true);
        when(securityService.getUserId()).thenReturn(userId);

        List<Card> cards = new ArrayList<>();
        cards.add(testCard);
        Page<Card> cardPage = new PageImpl<>(cards);

        Pageable pageable = PageRequest.of(0, 10);

        when(specificationBuilder.build(any(CardFilterDto.class))).thenReturn(mock(Specification.class));
        when(cardService.findPage(any(Specification.class), any(Pageable.class))).thenReturn(cardPage);
        when(modelMapper.map(any(Card.class), eq(CardReadDto.class))).thenReturn(cardReadDto);

        cardController.getAllEntities(cardFilterDto, pageable);

        assertEquals(userId, cardFilterDto.getUserId());
    }

    @Test
    void getAllEntities_ShouldNotModifyFilterDto_WhenNotUserRole() {

        when(securityService.hasRole("ROLE_USER")).thenReturn(false);

        List<Card> cards = new ArrayList<>();
        cards.add(testCard);
        Page<Card> cardPage = new PageImpl<>(cards);

        Pageable pageable = PageRequest.of(0, 10);

        when(specificationBuilder.build(any(CardFilterDto.class))).thenReturn(mock(Specification.class));
        when(cardService.findPage(any(Specification.class), any(Pageable.class))).thenReturn(cardPage);
        when(modelMapper.map(any(Card.class), eq(CardReadDto.class))).thenReturn(cardReadDto);

        cardController.getAllEntities(cardFilterDto, pageable);

        assertNull(cardFilterDto.getUserId());
    }

    @Test
    void getEntityById_ShouldCallSuperGetEntityById_WhenAdmin() {

        when(securityService.hasRole("ROLE_ADMIN")).thenReturn(true);
        when(cardService.findById(1L)).thenReturn(testCard);
        when(modelMapper.map(testCard, CardReadDto.class)).thenReturn(cardReadDto);

        CardReadDto result = cardController.getEntityById(1L);

        assertEquals(cardReadDto, result);
    }

    @Test
    void getEntityById_ShouldCallSuperGetEntityById_WhenCardBelongsToUser() {

        when(securityService.hasRole("ROLE_ADMIN")).thenReturn(false);
        when(securityService.checkCardBelonging(1L)).thenReturn(true);
        when(cardService.findById(1L)).thenReturn(testCard);
        when(modelMapper.map(testCard, CardReadDto.class)).thenReturn(cardReadDto);

        CardReadDto result = cardController.getEntityById(1L);

        assertEquals(cardReadDto, result);
    }

    @Test
    void getEntityById_ShouldThrowIllegalStateException_WhenNotAdminAndCardDoesNotBelongToUser() {

        when(securityService.hasRole("ROLE_ADMIN")).thenReturn(false);
        when(securityService.checkCardBelonging(1L)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> cardController.getEntityById(1L));
    }

    @Test
    void changeCardStatus_ShouldReturnUpdatedCardReadDto() {

        Status newStatus = new Status();
        newStatus.setId(3L);

        Card updatedCard = new Card();
        updatedCard.setId(1L);
        updatedCard.setStatus(newStatus);

        CardReadDto updatedCardDto = new CardReadDto();
        updatedCardDto.setId(1L);

        when(modelMapper.map(cardPatchDto, Card.class)).thenReturn(updatedCard);
        when(cardService.changeCardStatus(1L, newStatus)).thenReturn(updatedCard);
        when(cardService.findById(1L)).thenReturn(updatedCard);
        when(modelMapper.map(updatedCard, CardReadDto.class)).thenReturn(updatedCardDto);

        CardReadDto result = cardController.changeCardStatus(1L, cardPatchDto);

        assertEquals(updatedCardDto, result);
    }

    @Test
    void makeTransfer_ShouldCallCardServiceDoTransfer_WhenCardsOwnershipVerified() {

        long fromCardId = 1L;
        long toCardId = 2L;
        BigDecimal amount = new BigDecimal("100.00");

        TransferDto transferDto = new TransferDto();
        transferDto.setToCardId(toCardId);
        transferDto.setAmount(amount);

        when(securityService.checkCardBelonging(fromCardId)).thenReturn(true);
        when(securityService.checkCardBelonging(toCardId)).thenReturn(true);

        cardController.makeTransfer(fromCardId, transferDto);

        verify(cardService).doTransfer(fromCardId, toCardId, amount);
    }

    @Test
    void makeTransfer_ShouldThrowIllegalStateException_WhenSenderCardDoesNotBelongToUser() {

        long fromCardId = 1L;
        long toCardId = 2L;

        TransferDto transferDto = new TransferDto();
        transferDto.setToCardId(toCardId);

        when(securityService.checkCardBelonging(fromCardId)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> cardController.makeTransfer(fromCardId, transferDto));
    }

    @Test
    void makeTransfer_ShouldThrowIllegalStateException_WhenRecipientCardDoesNotBelongToUser() {

        long fromCardId = 1L;
        long toCardId = 2L;

        TransferDto transferDto = new TransferDto();
        transferDto.setToCardId(toCardId);

        when(securityService.checkCardBelonging(fromCardId)).thenReturn(true);
        when(securityService.checkCardBelonging(toCardId)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> cardController.makeTransfer(fromCardId, transferDto));
    }
}