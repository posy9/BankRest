package com.example.bankcards.security.service;

import com.example.bankcards.dto.userdtos.UserUpdateDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private static final Long ADMIN_ROLE_ID = 1L;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((User) auth.getPrincipal()).getId();
    }

    public boolean checkCardBelonging(Long cardId) {
        Long currentUserId = getUserId();
        Optional<Card> card = cardRepository.findById(cardId);

        return card.isPresent() && Objects.equals(card.get().getUser().getId(), currentUserId);
    }

    public boolean isAdmin(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() && user.get().getRole().getName().equals("ROLE_ADMIN");
    }

    public boolean checkUpdatePossibility(Long id, UserUpdateDto userUpdateDto) {
        boolean isAdminRole = ADMIN_ROLE_ID.equals(userUpdateDto.getRole().getId());
        boolean hasCard = cardRepository.findFirstByUser_id(id).isPresent();

        return !(isAdminRole && hasCard);
    }


}
