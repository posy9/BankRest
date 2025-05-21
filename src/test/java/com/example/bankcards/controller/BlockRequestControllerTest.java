package com.example.bankcards.controller;

import com.example.bankcards.dto.carddtos.CardForBlockDto;
import com.example.bankcards.dto.requestdtos.BlockRequestCreateDto;
import com.example.bankcards.security.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlockRequestControllerTest {


    @Mock
    private SecurityService securityService;

    @InjectMocks
    private BlockRequestController blockRequestController;

    @Test
    void createEntity_whenCardDoesNotBelongToUser_thenThrowIllegalStateException() {

        BlockRequestCreateDto createDto = new BlockRequestCreateDto();
        CardForBlockDto cardDto = new CardForBlockDto();
        cardDto.setId(1L);
        createDto.setCard(cardDto);

        when(securityService.checkCardBelonging(1L)).thenReturn(false);

        assertThrows(
                IllegalStateException.class,
                () -> blockRequestController.createEntity(createDto)
        );
    }
}
