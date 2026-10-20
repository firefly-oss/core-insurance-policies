/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.insurance.policies.core.services.impl;

import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import com.firefly.core.insurance.policies.core.mappers.PolicyStatusHistoryMapper;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyStatusHistoryDTO;
import com.firefly.core.insurance.policies.models.entities.PolicyStatusHistory;
import com.firefly.core.insurance.policies.models.repositories.PolicyStatusHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PolicyStatusHistoryServiceImplTest {

    @Mock
    private PolicyStatusHistoryRepository repository;

    @Mock
    private PolicyStatusHistoryMapper mapper;

    @InjectMocks
    private PolicyStatusHistoryServiceImpl service;

    private PolicyStatusHistoryDTO dto;
    private PolicyStatusHistory entity;
    private UUID policyStatusHistoryId;
    private UUID policyId;

    @BeforeEach
    void setUp() {
        policyStatusHistoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        entity = new PolicyStatusHistory();
        entity.setPolicyStatusHistoryId(policyStatusHistoryId);
        entity.setCreatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        entity.setUpdatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        dto = new PolicyStatusHistoryDTO();
        dto.setPolicyStatusHistoryId(policyStatusHistoryId);
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        policyId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
        entity.setPolicyId(policyId);
        dto.setPolicyId(policyId);
    }

    @Test
    void create_ShouldReturnDto_WhenSaveSucceeds() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.createPolicyStatusHistory(policyId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(repository).save(entity);
    }

    @Test
    void getById_ShouldReturnDto_WhenFound() {
        when(repository.findById(policyStatusHistoryId)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.getPolicyStatusHistoryById(policyId, policyStatusHistoryId))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenMissing() {
        when(repository.findById(policyStatusHistoryId)).thenReturn(Mono.empty());

        StepVerifier.create(service.getPolicyStatusHistoryById(policyId, policyStatusHistoryId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void update_ShouldSaveExistingRow() {
        when(repository.findById(policyStatusHistoryId)).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.updatePolicyStatusHistory(policyId, policyStatusHistoryId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).updateEntityFromDto(dto, entity);
    }

    @Test
    void delete_ShouldDelete_WhenFound() {
        when(repository.findById(policyStatusHistoryId)).thenReturn(Mono.just(entity));
        when(repository.deleteById(policyStatusHistoryId)).thenReturn(Mono.empty());

        StepVerifier.create(service.deletePolicyStatusHistory(policyId, policyStatusHistoryId))
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenParentDoesNotMatch() {
        entity.setPolicyId(UUID.fromString("323e4567-e89b-12d3-a456-426614174000"));
        when(repository.findById(policyStatusHistoryId)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.getPolicyStatusHistoryById(policyId, policyStatusHistoryId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
