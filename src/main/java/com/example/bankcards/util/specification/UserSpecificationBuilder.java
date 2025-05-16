package com.example.bankcards.util.specification;

import com.example.bankcards.dto.userdtos.UserFilterDto;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder implements SpecificationBuilder<User, UserFilterDto> {

    @Override
    public Specification<User> build(UserFilterDto filterDto) {
        return withRoleId(filterDto.getRole_id())
                .and(withNameLike(filterDto.getUsername()));
    }

    private Specification<User> withRoleId(Long roleId) {
        return ((root, query, criteriaBuilder) -> roleId == null
                ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("role").get("id"), roleId));
    }

    private Specification<User> withNameLike(String username) {
        return (root, query, criteriaBuilder) ->
                (username == null || username.isEmpty())
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")),
                        "%" + username + "%"
                );
    }
}
