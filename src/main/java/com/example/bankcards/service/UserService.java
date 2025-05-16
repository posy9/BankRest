package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.repository.StatusRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bankcards.util.registry.EntityNameRegistry.USER;

@Service
@Transactional
public class UserService extends AbstractService<User> {

    public UserService(UserRepository repository, StatusRepository statusRepository) {
        super(repository, USER);
    }
}
