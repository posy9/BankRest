package com.example.bankcards.service;

import com.example.bankcards.entity.DataEntity;
import com.example.bankcards.repository.EntityRepository;
import com.example.bankcards.util.registry.EntityNameRegistry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Optional;

import static com.example.bankcards.util.registry.ErrorMessagesRegistry.ENTITIES_NOT_FOUND;
import static com.example.bankcards.util.registry.ErrorMessagesRegistry.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
public abstract class AbstractService<T extends DataEntity> {

    protected final EntityRepository<T, Long> entityRepository;
    private final EntityNameRegistry EntityName;

    public T findById(long id) {
        Optional<T> foundEntity = entityRepository.findById(id);
        if (foundEntity.isPresent()) {
            return foundEntity.get();
        } else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(), EntityName.getEntityName(), id));
        }
    }

    public T createEntity(T entity) {
        entityRepository.save(entity);
        return entity;
    }

    public T updateEntity(long id, T entity) {
        if (entityRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(),
                    EntityName.getEntityName(), id));
        }
        entity.setId(id);
        return entityRepository.save(entity);
    }

    public void deleteEntity(long id) {
        Optional<T> entityForDelete = entityRepository.findById(id);
        if (entityForDelete.isPresent()) {
            T entity = entityForDelete.get();
            entityRepository.delete(entity);
        } else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND.getMessage(), EntityName.getEntityName(), id));
        }
    }

    public Page<T> findMultiple(Specification<T> specification, Pageable pageable) {
        Page<T> foundEntities = entityRepository.findAll(specification, pageable);
        if (!foundEntities.isEmpty()) {
            return foundEntities;
        } else {
            throw new EntityNotFoundException(ENTITIES_NOT_FOUND.getMessage());
        }
    }

}
