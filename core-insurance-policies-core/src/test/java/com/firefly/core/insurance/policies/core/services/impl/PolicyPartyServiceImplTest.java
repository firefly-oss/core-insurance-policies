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
import com.firefly.core.insurance.policies.core.mappers.PolicyPartyMapper;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyPartyDTO;
import com.firefly.core.insurance.policies.models.entities.PolicyParty;
import com.firefly.core.insurance.policies.models.repositories.PolicyPartyRepository;
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
class PolicyPartyServiceImplTest {

    @Mock
    private PolicyPartyRepository repository;

    @Mock
    private PolicyPartyMapper mapper;

    @InjectMocks
    private PolicyPartyServiceImpl service;

    private PolicyPartyDTO dto;
    private PolicyParty entity;
    private UUID policyPartyId;
    private UUID policyId;

    @BeforeEach
    void setUp() {
        policyPartyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        entity = new PolicyParty();
        entity.setPolicyPartyId(policyPartyId);
        entity.setCreatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        entity.setUpdatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        dto = new PolicyPartyDTO();
        dto.setPolicyPartyId(policyPartyId);
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

        StepVerifier.create(service.createPolicyParty(policyId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(repository).save(entity);
    }

    @Test
    void getById_ShouldReturnDto_WhenFound() {
        when(repository.findById(policyPartyId)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.getPolicyPartyById(policyId, policyPartyId))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenMissing() {
        when(repository.findById(policyPartyId)).thenReturn(Mono.empty());

        StepVerifier.create(service.getPolicyPartyById(policyId, policyPartyId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void update_ShouldSaveExistingRow() {
        when(repository.findById(policyPartyId)).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.updatePolicyParty(policyId, policyPartyId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).updateEntityFromDto(dto, entity);
    }

    @Test
    void delete_ShouldDelete_WhenFound() {
        when(repository.findById(policyPartyId)).thenReturn(Mono.just(entity));
        when(repository.deleteById(policyPartyId)).thenReturn(Mono.empty());

        StepVerifier.create(service.deletePolicyParty(policyId, policyPartyId))
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenParentDoesNotMatch() {
        entity.setPolicyId(UUID.fromString("323e4567-e89b-12d3-a456-426614174000"));
        when(repository.findById(policyPartyId)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.getPolicyPartyById(policyId, policyPartyId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
