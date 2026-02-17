package com.fiap.infrastructure.gateway;

import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.gateway.PartGatewayImpl;

import com.fiap.mapper.part.PartMapper;
import com.fiap.persistence.entity.PartEntity;
import com.fiap.persistence.repository.PartJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PartGatewayImplTest {

    @Mock
    private PartJpaRepository partRepository;

    @Mock
    private PartMapper partMapper;

    @InjectMocks
    private PartGatewayImpl partGateway;

    private UUID id;
    private Part domainPart;
    private PartEntity entityPart;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        id = UUID.randomUUID();

        domainPart = Part.builder()
                .id(id)
                .name("Filtro de Óleo")
                .build();

        entityPart = new PartEntity();
        entityPart.setId(id);
        entityPart.setName("Filtro de Óleo");
    }

    @Test
    void shouldReturnPartWhenFound() throws BusinessRuleException, NotFoundException {
        when(partRepository.findById(id)).thenReturn(Optional.of(entityPart));
        when(partMapper.toDomain(entityPart)).thenReturn(domainPart);

        Optional<Part> result = partGateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domainPart, result.get());

        verify(partRepository).findById(id);
        verify(partMapper).toDomain(entityPart);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPartNotFound() throws BusinessRuleException {
        when(partRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> partGateway.findById(id));

        verify(partRepository).findById(id);
        verify(partMapper, never()).toDomain(any(PartEntity.class));
    }

    @Test
    void shouldSavePartSuccessfully() throws BusinessRuleException {
        when(partMapper.toEntity(domainPart)).thenReturn(entityPart);

        partGateway.save(domainPart);

        verify(partMapper).toEntity(domainPart);
        verify(partRepository).save(entityPart);
        verify(partMapper, never()).toDomain(any(PartEntity.class));
    }
}
