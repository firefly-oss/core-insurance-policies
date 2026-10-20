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
import com.firefly.core.insurance.policies.core.mappers.MotorDetailMapper;
import com.firefly.core.insurance.policies.interfaces.dtos.MotorDetailDTO;
import com.firefly.core.insurance.policies.models.entities.MotorDetail;
import com.firefly.core.insurance.policies.models.repositories.MotorDetailRepository;
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
class MotorDetailServiceImplTest {

    @Mock
    private MotorDetailRepository repository;

    @Mock
    private MotorDetailMapper mapper;

    @InjectMocks
    private MotorDetailServiceImpl service;

    private MotorDetailDTO dto;
    private MotorDetail entity;
    private UUID motorDetailId;
    private UUID policyId;

    @BeforeEach
    void setUp() {
        motorDetailId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        entity = new MotorDetail();
        entity.setMotorDetailId(motorDetailId);
        entity.setCreatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        entity.setUpdatedAt(LocalDateTime.parse("2026-01-15T10:15:30"));
        dto = new MotorDetailDTO();
        dto.setMotorDetailId(motorDetailId);
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

        StepVerifier.create(service.createMotorDetail(policyId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(repository).save(entity);
    }

    @Test
    void getById_ShouldReturnDto_WhenFound() {
        when(repository.findById(motorDetailId)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.getMotorDetailById(policyId, motorDetailId))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenMissing() {
        when(repository.findById(motorDetailId)).thenReturn(Mono.empty());

        StepVerifier.create(service.getMotorDetailById(policyId, motorDetailId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void update_ShouldSaveExistingRow() {
        when(repository.findById(motorDetailId)).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.updateMotorDetail(policyId, motorDetailId, dto))
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).updateEntityFromDto(dto, entity);
    }

    @Test
    void delete_ShouldDelete_WhenFound() {
        when(repository.findById(motorDetailId)).thenReturn(Mono.just(entity));
        when(repository.deleteById(motorDetailId)).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteMotorDetail(policyId, motorDetailId))
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenParentDoesNotMatch() {
        entity.setPolicyId(UUID.fromString("323e4567-e89b-12d3-a456-426614174000"));
        when(repository.findById(motorDetailId)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.getMotorDetailById(policyId, motorDetailId))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
