package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.example.bankcards.util.registry.EntityNameRegistry.CARD;
import static com.example.bankcards.util.registry.ErrorMessagesRegistry.*;

@Service
@Transactional
public class CardService extends AbstractService<Card> {

    private static final long ACTIVE_STATUS_ID = 2L;

    private final StatusRepository statusRepository;
    private final CardRepository cardRepository;

    public CardService(CardRepository repository, StatusRepository statusRepository) {
        super(repository, CARD);
        this.statusRepository = statusRepository;
        this.cardRepository = repository;
    }

    @Override
    public Card createEntity(Card card) {
        Status status = statusRepository.getReferenceById(ACTIVE_STATUS_ID);
        card.setStatus(status);
        entityRepository.save(card);
        return card;
    }

    public Card changeCardStatus(long id, Status status) {
        Card card = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(), CARD.getEntityName(), id)));
        card.setStatus(status);
        return entityRepository.save(card);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public int updateExpiredCards() {
        return cardRepository.updateExpiredCards();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void doTransfer(Long fromCardId, Long toCardId, BigDecimal amount) {
        Card fromCard = cardRepository.findById(fromCardId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(),
                        CARD.getEntityName(), fromCardId)));

        Card toCard = cardRepository.findById(toCardId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(),
                        CARD.getEntityName(), toCardId)));

        if (checkTransferPossibility(fromCard, toCard, amount)) {
            fromCard.setBalance(fromCard.getBalance().subtract(amount));
            toCard.setBalance(toCard.getBalance().add(amount));
            cardRepository.save(fromCard);
            cardRepository.save(toCard);
        }
    }

    private boolean checkTransferPossibility(Card fromCard, Card toCard, BigDecimal amount) {

        if (!fromCard.getStatus().getId().equals(ACTIVE_STATUS_ID)) {
            throw new IllegalStateException(SENDER_CARD.getMessage());
        }

        if (fromCard.getId().equals(toCard.getId())) {
            throw new IllegalStateException(SAME_CARD.getMessage());
        }

        if (!toCard.getStatus().getId().equals(ACTIVE_STATUS_ID)) {
            throw new IllegalStateException(RECIPIENT_CARD.getMessage());
        }

        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(FUNDS.getMessage());
        }
        return true;
    }
}
