package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private CardService cardService;

    private Card testCard;
    private Status activeStatus;
    private Status blockedStatus;
    private User cardUser;

    @BeforeEach
    void setUp() {
        cardUser = new User();
        cardUser.setId(1L);

        activeStatus = new Status();
        activeStatus.setId(2L);
        activeStatus.setName("Active");

        blockedStatus = new Status();
        blockedStatus.setId(1L);
        blockedStatus.setName("BLOCKED");

        testCard = new Card();
        testCard.setId(1L);
        testCard.setBalance(new BigDecimal("1000.00"));
        testCard.setStatus(activeStatus);
        testCard.setCardNumber("4111111111111111");
        testCard.setExpiryDate(LocalDate.of(2026, 5, 31));
        testCard.setUser(cardUser);
    }

    @Test
    void createEntity_ShouldReturnCardWithActiveStatus() {

        Card cardToCreate = new Card();
        cardToCreate.setBalance(new BigDecimal("500.00"));
        cardToCreate.setCardNumber("4111111111111111");
        cardToCreate.setExpiryDate(LocalDate.of(2026, 5, 31));
        cardToCreate.setUser(cardUser);

        Card expectedCard = new Card();
        expectedCard.setId(1L);
        expectedCard.setBalance(new BigDecimal("500.00"));
        expectedCard.setCardNumber("4111111111111111");
        expectedCard.setExpiryDate(LocalDate.of(2026, 5, 31));
        expectedCard.setUser(cardUser);
        expectedCard.setStatus(activeStatus);

        when(statusRepository.getReferenceById(2L)).thenReturn(activeStatus);
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            card.setId(1L);
            return card;
        });

        Card result = cardService.createEntity(cardToCreate);

        assertEquals(expectedCard, result);
    }

    @Test
    void changeCardStatus_ShouldReturnCardWithUpdatedCardStatus() {

        Card expectedCard = new Card();
        expectedCard.setId(1L);
        expectedCard.setBalance(new BigDecimal("1000.00"));
        expectedCard.setCardNumber("4111111111111111");
        expectedCard.setExpiryDate(LocalDate.of(2026, 5, 31));
        expectedCard.setUser(cardUser);
        expectedCard.setStatus(blockedStatus);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(testCard));
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        Card result = cardService.changeCardStatus(1L, blockedStatus);

        assertEquals(expectedCard, result);
    }

    @Test
    void changeCardStatus_ShouldThrowEntityNotFoundException_WhenCardForUpdateNotFound() {

        when(cardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardService.changeCardStatus(99L, blockedStatus));
    }

    @Test
    void updateExpiredCards_ShouldReturnNumberOfUpdatedCards() {

        when(cardRepository.updateExpiredCards()).thenReturn(5);

        int result = cardService.updateExpiredCards();

        assertEquals(5, result);
    }

    @Test
    void doTransfer_ShouldUpdateBalances_WhenBothCardsAreValid() {

        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setBalance(new BigDecimal("1000.00"));
        fromCard.setUser(cardUser);
        fromCard.setStatus(activeStatus);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setBalance(new BigDecimal("500.00"));
        toCard.setUser(cardUser);
        toCard.setStatus(activeStatus);

        BigDecimal transferAmount = new BigDecimal("300.00");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        cardService.doTransfer(1L, 2L, transferAmount);

        assertEquals(new BigDecimal("700.00"), fromCard.getBalance());
        assertEquals(new BigDecimal("800.00"), toCard.getBalance());
    }

    @Test
    void doTransfer_ShouldThrowException_WhenTransferToTheSameCard() {

        Card card = new Card();
        card.setId(1L);
        card.setBalance(new BigDecimal("1000.00"));
        card.setUser(cardUser);
        card.setStatus(activeStatus);


        BigDecimal transferAmount = new BigDecimal("300.00");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class, ()->cardService.doTransfer(1L, 1L, transferAmount));
    }



    @Test
    void doTransfer_ShouldThrowExceptionWhenFromCardNotFound() {

        when(cardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                cardService.doTransfer(99L, 2L, new BigDecimal("100.00")));
    }

    @Test
    void doTransfer_ShouldThrowExceptionWhenToCardNotFound() {

        when(cardRepository.findById(1L)).thenReturn(Optional.of(testCard));
        when(cardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                cardService.doTransfer(1L, 99L, new BigDecimal("100.00")));
    }

    @Test
    void doTransfer_ShouldThrowException_WhenFromCardInactive() {

        Card inactiveCard = new Card();
        inactiveCard.setId(1L);
        inactiveCard.setBalance(new BigDecimal("1000.00"));
        inactiveCard.setStatus(blockedStatus);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setBalance(new BigDecimal("500.00"));
        toCard.setStatus(activeStatus);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(inactiveCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        assertThrows(IllegalStateException.class, () ->
                cardService.doTransfer(1L, 2L, new BigDecimal("100.00")));
    }

    @Test
    void doTransfer_ShouldThrowException_WhenToCardInactive() {

        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setBalance(new BigDecimal("1000.00"));
        fromCard.setStatus(activeStatus);

        Card inactiveToCard = new Card();
        inactiveToCard.setId(2L);
        inactiveToCard.setBalance(new BigDecimal("500.00"));
        inactiveToCard.setStatus(blockedStatus);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(inactiveToCard));

        assertThrows(IllegalStateException.class, () ->
                cardService.doTransfer(1L, 2L, new BigDecimal("100.00")));
    }

    @Test
    void doTransfer_ShouldThrowException_WhenInsufficientFunds() {
        // Arrange
        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setBalance(new BigDecimal("100.00"));
        fromCard.setStatus(activeStatus);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setBalance(new BigDecimal("500.00"));
        toCard.setStatus(activeStatus);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                cardService.doTransfer(1L, 2L, new BigDecimal("200.00")));
    }

    @Test
    void doTransfer_ShouldCallSaveForBothCards() {

        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setBalance(new BigDecimal("1000.00"));
        fromCard.setStatus(activeStatus);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setBalance(new BigDecimal("500.00"));
        toCard.setStatus(activeStatus);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        cardService.doTransfer(1L, 2L, new BigDecimal("300.00"));

        verify(cardRepository, times(2)).save(any(Card.class));
    }
}