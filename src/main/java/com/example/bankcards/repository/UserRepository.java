package com.example.bankcards.repository;

import com.example.bankcards.entity.User;

import java.util.Optional;

public interface UserRepository extends EntityRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
