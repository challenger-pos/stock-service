package com.fiap.persistence.repository;

import com.fiap.persistence.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartJpaRepository extends JpaRepository<PartEntity, UUID> {
}
