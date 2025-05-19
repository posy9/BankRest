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
    CARDS_BELONG("Cards do not belong to the user or do not exist"),
    CARD_BELONG("Card does not belong to the user or does not exist"),
    SENDER_CARD("The sender card is blocked or expired"),
    SAME_CARD("Сan not transfer to the same card"),
    RECIPIENT_CARD("The recipient card is blocked or expired"),
    CARD_HOLDING("Administrators can't hold cards"),
    FUNDS("Not enough funds"),
    ADMIN_DELETING("Unable to delete administrator"),
    ADMIN_UPDATING("Administrator can't update other administrators"),
    ;

    private final String message;
}
