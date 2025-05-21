package com.example.bankcards.controller;

import com.example.bankcards.dto.userdtos.UserUpdateDto;
import com.example.bankcards.security.service.SecurityService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.specification.UserSpecificationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @Mock
    private UserSpecificationBuilder userSpecificationBuilder;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private UserController userController;

    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void setUp() {
        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername("user");
        userUpdateDto.setPassword("123456");
    }

    @Test
    void updateEntity_ShouldThrowException_WhenMakeAdminUserWithCards() {

        when(securityService.getUserId()).thenReturn(1L);
        when(securityService.checkUpdatePossibility(2L, userUpdateDto)).thenReturn(false);

        assertThrows(IllegalStateException.class, () ->
                userController.updateEntity(2L, userUpdateDto)
        );
    }


    @Test
    void updateEntity_ShouldThrowException_whenAdminTriesToUpdateAnotherAdmin() {

        when(securityService.getUserId()).thenReturn(1L);
        when(securityService.checkUpdatePossibility(2L, userUpdateDto)).thenReturn(true);
        when(securityService.isAdmin(2L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () ->
                userController.updateEntity(2L, userUpdateDto)
        );
    }

    @Test
    void deleteEntity_ShouldThrowException_WhenAdminDeletingAttempt() {

        when(securityService.isAdmin(1L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () ->
                userController.deleteEntity(1L)
        );
    }
}