package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bankcards.util.registry.EntityNameRegistry.CARD;
import static com.example.bankcards.util.registry.ErrorMessagesRegistry.ENTITY_NOT_FOUND;

@Service
@Transactional
public class CardService extends AbstractService<Card> {

    private final StatusRepository statusRepository;
    private final CardRepository cardRepository;

    public CardService(CardRepository repository, StatusRepository statusRepository) {
        super(repository, CARD);
        this.statusRepository = statusRepository;
        this.cardRepository = repository;
    }

    @Override
    public Card createEntity(Card card) {
        Status status = statusRepository.getReferenceById(2L);
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
}
