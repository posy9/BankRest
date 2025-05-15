package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateDto;
import com.example.bankcards.dto.FilterDto;
import com.example.bankcards.dto.ReadDto;
import com.example.bankcards.dto.UpdateDto;
import com.example.bankcards.entity.DataEntity;
import com.example.bankcards.service.AbstractService;
import com.example.bankcards.util.specification.SpecificationBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public abstract class AbstractController<T extends DataEntity, R extends ReadDto, C extends CreateDto, U extends UpdateDto, F extends FilterDto> {

    protected final ModelMapper modelMapper;
    private final AbstractService<T> entityService;
    private final Class<T> entityClass;
    private final Class<R> readDtoClass;
    private final SpecificationBuilder<T, F> entitySpecificationBuilder;

    @GetMapping
    Page<R> getAllEntities(@ModelAttribute F filterDto, @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Specification<T> entitySpecification = entitySpecificationBuilder.build(filterDto);
        Page<T> entities = entityService.findMultiple(entitySpecification, pageable);
        return entities.map(entity -> modelMapper.map(entity, readDtoClass));
    }

    @GetMapping(value = "/{id}")
    R getEntityById(@PathVariable long id) {
        T entity = entityService.findById(id);
        return modelMapper.map(entity, readDtoClass);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public R createEntity(@Valid @RequestBody C entityCreateDto) {
        T entity = modelMapper.map(entityCreateDto, entityClass);
        entityService.createEntity(entity);
        T createdEntity = entityService.findById(entity.getId());
        return modelMapper.map(createdEntity, readDtoClass);
    }

    @PutMapping(value = "/{id}")
    R updateEntity(@PathVariable long id, @Valid @RequestBody U entityUpdateDto) {
        entityService.updateEntity(id, modelMapper.map(entityUpdateDto, entityClass));
        T updatedEntity = entityService.findById(id);
        return modelMapper.map(updatedEntity, readDtoClass);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteEntity(@PathVariable long id) {
        entityService.deleteEntity(id);
        return ResponseEntity.noContent().build();
    }
}
