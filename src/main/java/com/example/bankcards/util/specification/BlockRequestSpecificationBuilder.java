package com.example.bankcards.util.specification;

import com.example.bankcards.dto.requestdtos.BlockRequestFilterDto;
import com.example.bankcards.entity.BlockRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BlockRequestSpecificationBuilder implements SpecificationBuilder<BlockRequest, BlockRequestFilterDto> {

    @Override
    public Specification<BlockRequest> build(BlockRequestFilterDto filterDto) {
        return withCardId(filterDto.getCardId());
    }

    private Specification<BlockRequest> withCardId(Long cardId) {
        return ((root, query, criteriaBuilder) -> cardId == null
                ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("card").get("id"), cardId));
    }
}
