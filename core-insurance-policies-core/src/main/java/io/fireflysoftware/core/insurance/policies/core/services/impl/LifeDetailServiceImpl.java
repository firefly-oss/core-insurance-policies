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


package io.fireflysoftware.core.insurance.policies.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import io.fireflysoftware.core.insurance.policies.core.mappers.LifeDetailMapper;
import io.fireflysoftware.core.insurance.policies.core.services.LifeDetailService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.LifeDetailDTO;
import io.fireflysoftware.core.insurance.policies.models.entities.LifeDetail;
import io.fireflysoftware.core.insurance.policies.models.repositories.LifeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LifeDetailServiceImpl implements LifeDetailService {

    @Autowired
    private LifeDetailRepository repository;

    @Autowired
    private LifeDetailMapper mapper;

    @Override
    public Mono<PaginationResponse<LifeDetailDTO>> filterLifeDetails(UUID policyId, FilterRequest<LifeDetailDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new LifeDetailDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(LifeDetail.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<LifeDetailDTO> createLifeDetail(UUID policyId, LifeDetailDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LifeDetailDTO> updateLifeDetail(UUID policyId, UUID lifeDetailId, LifeDetailDTO dto) {
        return repository.findById(lifeDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("LifeDetail", lifeDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "LifeDetail with id '" + lifeDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLifeDetail(UUID policyId, UUID lifeDetailId) {
        return repository.findById(lifeDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("LifeDetail", lifeDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "LifeDetail with id '" + lifeDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(lifeDetailId);
                });
    }

    @Override
    public Mono<LifeDetailDTO> getLifeDetailById(UUID policyId, UUID lifeDetailId) {
        return repository.findById(lifeDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("LifeDetail", lifeDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "LifeDetail with id '" + lifeDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
