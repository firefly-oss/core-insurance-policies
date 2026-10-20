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
import io.fireflysoftware.core.insurance.policies.core.mappers.PolicyStatusHistoryMapper;
import io.fireflysoftware.core.insurance.policies.core.services.PolicyStatusHistoryService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.PolicyStatusHistoryDTO;
import io.fireflysoftware.core.insurance.policies.models.entities.PolicyStatusHistory;
import io.fireflysoftware.core.insurance.policies.models.repositories.PolicyStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class PolicyStatusHistoryServiceImpl implements PolicyStatusHistoryService {

    @Autowired
    private PolicyStatusHistoryRepository repository;

    @Autowired
    private PolicyStatusHistoryMapper mapper;

    @Override
    public Mono<PaginationResponse<PolicyStatusHistoryDTO>> filterPolicyStatusHistories(UUID policyId, FilterRequest<PolicyStatusHistoryDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new PolicyStatusHistoryDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(PolicyStatusHistory.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<PolicyStatusHistoryDTO> createPolicyStatusHistory(UUID policyId, PolicyStatusHistoryDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PolicyStatusHistoryDTO> updatePolicyStatusHistory(UUID policyId, UUID policyStatusHistoryId, PolicyStatusHistoryDTO dto) {
        return repository.findById(policyStatusHistoryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyStatusHistory", policyStatusHistoryId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyStatusHistory with id '" + policyStatusHistoryId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePolicyStatusHistory(UUID policyId, UUID policyStatusHistoryId) {
        return repository.findById(policyStatusHistoryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyStatusHistory", policyStatusHistoryId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyStatusHistory with id '" + policyStatusHistoryId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(policyStatusHistoryId);
                });
    }

    @Override
    public Mono<PolicyStatusHistoryDTO> getPolicyStatusHistoryById(UUID policyId, UUID policyStatusHistoryId) {
        return repository.findById(policyStatusHistoryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyStatusHistory", policyStatusHistoryId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyStatusHistory with id '" + policyStatusHistoryId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
