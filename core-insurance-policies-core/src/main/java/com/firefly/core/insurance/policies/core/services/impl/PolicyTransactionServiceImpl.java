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
import com.firefly.core.insurance.policies.core.mappers.PolicyTransactionMapper;
import com.firefly.core.insurance.policies.core.services.PolicyTransactionService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyTransactionDTO;
import com.firefly.core.insurance.policies.models.entities.PolicyTransaction;
import com.firefly.core.insurance.policies.models.repositories.PolicyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class PolicyTransactionServiceImpl implements PolicyTransactionService {

    @Autowired
    private PolicyTransactionRepository repository;

    @Autowired
    private PolicyTransactionMapper mapper;

    @Override
    public Mono<PaginationResponse<PolicyTransactionDTO>> filterPolicyTransactions(UUID policyId, FilterRequest<PolicyTransactionDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new PolicyTransactionDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(PolicyTransaction.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<PolicyTransactionDTO> createPolicyTransaction(UUID policyId, PolicyTransactionDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PolicyTransactionDTO> updatePolicyTransaction(UUID policyId, UUID policyTransactionId, PolicyTransactionDTO dto) {
        return repository.findById(policyTransactionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyTransaction", policyTransactionId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyTransaction with id '" + policyTransactionId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePolicyTransaction(UUID policyId, UUID policyTransactionId) {
        return repository.findById(policyTransactionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyTransaction", policyTransactionId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyTransaction with id '" + policyTransactionId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(policyTransactionId);
                });
    }

    @Override
    public Mono<PolicyTransactionDTO> getPolicyTransactionById(UUID policyId, UUID policyTransactionId) {
        return repository.findById(policyTransactionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyTransaction", policyTransactionId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyTransaction with id '" + policyTransactionId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
