package com.example.bankcards.util.specification;

import com.example.bankcards.dto.userdtos.UserFilterDto;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder implements SpecificationBuilder<User, UserFilterDto> {

    @Override
    public Specification<User> build(UserFilterDto filterDto) {
        return null;
    }
}
