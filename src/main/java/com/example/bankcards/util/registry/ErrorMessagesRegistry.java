package com.example.bankcards.util.registry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessagesRegistry {
    ENTITY_NOT_FOUND("%s with id : %s not found"),
    ENTITY_EXISTS("This %s already exists"),
    ENTITIES_NOT_FOUND("Entities for your request are not found"),
    ENTITY_WITH_DEPENDENCIES("%s with id: %s has dependencies and can not be deleted."),
    SAME_ENTITY_EXISTS("The same entity already exists"),
    CARD_BELONG("Cards do not belong to the user"),
    SENDER_CARD("The sender card is blocked or expired"),
    SAME_CARD("Сan not transfer to the same card"),
    RECIPIENT_CARD("The recipient card is blocked or expired"),
    FUNDS("Not enough funds");

    private final String message;
}
