package com.example.bankcards.util.specification;

import com.example.bankcards.dto.carddtos.CardFilterDto;
import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CardSpecificationBuilder {

    public Specification<Card> build(CardFilterDto filterDto) {
        return withStatusId(filterDto.getStatus_id())
                .and(withExpiryDate(filterDto.getExpiryDate())
                        .and(withUserId(filterDto.getUser_id()))
                        .and(withNumberLike(filterDto.getCardNumber())));
    }

    private Specification<Card> withStatusId(Long statusId) {
        return ((root, query, criteriaBuilder) -> statusId == null
                ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status").get("id"), statusId));
    }

    private Specification<Card> withExpiryDate(String expiryDate) {
        return ((root, query, criteriaBuilder) -> expiryDate == null
                ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("type").get("id"), expiryDate));
    }

    private Specification<Card> withUserId(Long userId) {
        return ((root, query, criteriaBuilder) -> userId == null
                ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("user").get("id"), userId));
    }

    private Specification<Card> withNumberLike(String cardNumber) {
        return (root, query, criteriaBuilder) ->
                (cardNumber == null || cardNumber.isEmpty())
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("cardNumber")),
                        "%" + cardNumber + "%"
                );
    }
}
