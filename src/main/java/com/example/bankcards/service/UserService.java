package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.repository.StatusRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bankcards.util.registry.EntityNameRegistry.USER;

@Service
@Transactional
public class UserService extends AbstractService<User> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository repository, StatusRepository statusRepository) {
        super(repository, USER);
        this.userRepository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User createEntity(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.createEntity(user);
    }
}
