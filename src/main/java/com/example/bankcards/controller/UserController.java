package com.example.bankcards.controller;

import com.example.bankcards.dto.userdtos.UserCreateDto;
import com.example.bankcards.dto.userdtos.UserFilterDto;
import com.example.bankcards.dto.userdtos.UserReadDto;
import com.example.bankcards.dto.userdtos.UserUpdateDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.specification.UserSpecificationBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractController<User, UserReadDto, UserCreateDto, UserUpdateDto, UserFilterDto> {

    public UserController(ModelMapper modelMapper, UserService service, UserSpecificationBuilder userSpecificationBuilder) {
        super(modelMapper, service, User.class, UserReadDto.class, userSpecificationBuilder);
    }
}
