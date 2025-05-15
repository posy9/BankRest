package com.example.bankcards.util.specification;

import com.example.bankcards.dto.FilterDto;
import com.example.bankcards.entity.DataEntity;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T extends DataEntity, DTO extends FilterDto> {

    Specification<T> build(DTO filterDto);
}
