package com.example.bankcards.util.registry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EntityNameRegistry {
    CARD("Card"),
    USER("User"),
    BLOCK_REQUEST("BlockRequest"),
    ;

    private final String entityName;
}
