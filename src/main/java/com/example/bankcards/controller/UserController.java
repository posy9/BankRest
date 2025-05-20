package com.example.bankcards.controller;

import com.example.bankcards.dto.userdtos.UserCreateDto;
import com.example.bankcards.dto.userdtos.UserFilterDto;
import com.example.bankcards.dto.userdtos.UserReadDto;
import com.example.bankcards.dto.userdtos.UserUpdateDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.service.SecurityService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.specification.UserSpecificationBuilder;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.bankcards.util.registry.ErrorMessagesRegistry.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractController<User, UserReadDto, UserCreateDto, UserUpdateDto, UserFilterDto> {

    private final SecurityService securityService;

    public UserController(ModelMapper modelMapper, UserService service,
                          UserSpecificationBuilder userSpecificationBuilder, SecurityService securityService) {
        super(modelMapper, service, User.class, UserReadDto.class, userSpecificationBuilder);
        this.securityService = securityService;
    }

    @PutMapping(value = "/{id}")
    public UserReadDto updateEntity(@PathVariable long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        Long currentUserId = securityService.getUserId();
        if (currentUserId.equals(id)) {
            return super.updateEntity(id, userUpdateDto);
        }
        if (!securityService.isAdmin(id)) {
            return super.updateEntity(id, userUpdateDto);
        }
        if (!securityService.checkUpdatePossibility(id, userUpdateDto)) {
            throw new IllegalStateException(CARD_HOLDER.getMessage());
        }
        throw new IllegalStateException(ADMIN_UPDATING.getMessage());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("#id != authentication.principal.id")
    public ResponseEntity<Void> deleteEntity(@PathVariable long id) {
        if (!securityService.isAdmin(id)) {
            return super.deleteEntity(id);
        }
        throw new IllegalStateException(ADMIN_DELETING.getMessage());
    }
}
