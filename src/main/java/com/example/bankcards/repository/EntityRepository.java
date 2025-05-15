package com.example.bankcards.repository;

import com.example.bankcards.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T extends DataEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
