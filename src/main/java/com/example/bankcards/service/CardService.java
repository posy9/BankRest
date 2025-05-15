package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.bankcards.util.registry.EntityNameRegistry.CARD;
import static com.example.bankcards.util.registry.ErrorMessagesRegistry.ENTITIES_NOT_FOUND;
import static com.example.bankcards.util.registry.ErrorMessagesRegistry.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;

    public Card findById(long id) {
        Optional<Card> foundCard = cardRepository.findById(id);
        if (foundCard.isPresent()) {
            return foundCard.get();
        } else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(), CARD.getEntityName(), id));
        }
    }

    public Card createCard(Card card) {
        Status status = statusRepository.getReferenceById(2L);
        card.setStatus(status);
        cardRepository.save(card);
        return card;
    }

    public Card updateCard(long id, Card card) {
        if (cardRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(),
                    CARD.getEntityName(), id));
        }
        card.setId(id);
        return cardRepository.save(card);
    }

    public void deleteCard(long id) {
        Optional<Card> cardForDelete = cardRepository.findById(id);
        if (cardForDelete.isPresent()) {
            Card entity = cardForDelete.get();
            cardRepository.delete(entity);
        } else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(), CARD.getEntityName(), id));
        }
    }

    public Page<Card> findMultiple(Specification<Card> specification, Pageable pageable) {
        Page<Card> foundEntities = cardRepository.findAll(specification, pageable);
        if (!foundEntities.isEmpty()) {
            return foundEntities;
        } else {
            throw new EntityNotFoundException(ENTITIES_NOT_FOUND.getMessage());
        }
    }
}
