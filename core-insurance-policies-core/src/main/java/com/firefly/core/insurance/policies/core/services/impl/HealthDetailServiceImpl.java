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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import com.firefly.core.insurance.policies.core.mappers.HealthDetailMapper;
import com.firefly.core.insurance.policies.core.services.HealthDetailService;
import com.firefly.core.insurance.policies.interfaces.dtos.HealthDetailDTO;
import com.firefly.core.insurance.policies.models.entities.HealthDetail;
import com.firefly.core.insurance.policies.models.repositories.HealthDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class HealthDetailServiceImpl implements HealthDetailService {

    @Autowired
    private HealthDetailRepository repository;

    @Autowired
    private HealthDetailMapper mapper;

    @Override
    public Mono<PaginationResponse<HealthDetailDTO>> filterHealthDetails(UUID policyId, FilterRequest<HealthDetailDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new HealthDetailDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(HealthDetail.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<HealthDetailDTO> createHealthDetail(UUID policyId, HealthDetailDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<HealthDetailDTO> updateHealthDetail(UUID policyId, UUID healthDetailId, HealthDetailDTO dto) {
        return repository.findById(healthDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("HealthDetail", healthDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "HealthDetail with id '" + healthDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteHealthDetail(UUID policyId, UUID healthDetailId) {
        return repository.findById(healthDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("HealthDetail", healthDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "HealthDetail with id '" + healthDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(healthDetailId);
                });
    }

    @Override
    public Mono<HealthDetailDTO> getHealthDetailById(UUID policyId, UUID healthDetailId) {
        return repository.findById(healthDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("HealthDetail", healthDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "HealthDetail with id '" + healthDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
