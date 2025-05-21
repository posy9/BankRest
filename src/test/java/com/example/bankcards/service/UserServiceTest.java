package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Role userRole;

    @BeforeEach
    void setUp() {

        userRole = new Role();
        userRole.setId(2L);
        userRole.setName("ROLE_USER");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
    }

    @Test
    void loadUserByUsername_ShouldReturnUser_WhenUsernameExists() {

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        User result = userService.loadUserByUsername("testUser");

        assertEquals(testUser, result);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUsernameDoesNotExist() {

        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("nonexistent"));
    }

    @Test
    void createEntity_ShouldReturnUserWithEncodedPassword() {

        User userToCreate = new User();
        userToCreate.setUsername("newUser");
        userToCreate.setPassword("123456");
        userToCreate.setRole(userRole);

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("newUser");
        expectedUser.setPassword("123456");
        expectedUser.setRole(userRole);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User result = userService.createEntity(userToCreate);

        assertNotEquals(expectedUser.getPassword(), result.getPassword());
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expectedUser);
    }

    @Test
    void updateEntity_ShouldReturnUpdatedUserWithEncodedPassword_WhenUserExists() {

        User userToUpdate = new User();
        userToUpdate.setUsername("updatedUser");
        userToUpdate.setPassword("new_password");
        userToUpdate.setRole(userRole);

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("updatedUser");
        expectedUser.setPassword("new_password");
        expectedUser.setRole(userRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        User result = userService.updateEntity(1L, userToUpdate);

        assertNotEquals(expectedUser.getPassword(), result.getPassword());
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expectedUser);
    }

    @Test
    void updateEntity_ShouldThrowEntityNotFoundException_WhenUserDoesNotExist() {

        User userToUpdate = new User();
        userToUpdate.setUsername("updateduser");
        userToUpdate.setPassword("new_password");

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.updateEntity(99L, userToUpdate));
    }

    @Test
    void findById_ShouldReturnUser_WhenIdExists() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.findById(1L);

        assertEquals(testUser, result);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenIdDoesNotExist() {

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.findById(99L));
    }

    @Test
    void deleteEntity_ShouldThrowEntityNotFoundException_WhenIdDoesNotExist() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userService.deleteEntity(99L));
    }

    @Test
    void findPage_ShouldReturnPageOfUsers_WhenUsersExist() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);
        Page<User> userPage = new PageImpl<>(users);

        Specification<User> spec = mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.findPage(spec, pageable);

        assertEquals(userPage, result);
    }

    @Test
    void findPage_ShouldThrowEntityNotFoundException_WhenNoUsersFound() {
        // Arrange
        Page<User> emptyPage = new PageImpl<>(new ArrayList<>());

        Specification<User> spec = mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(emptyPage);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userService.findPage(spec, pageable));
    }
}