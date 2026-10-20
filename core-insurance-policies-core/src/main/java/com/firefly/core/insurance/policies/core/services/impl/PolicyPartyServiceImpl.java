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
import com.firefly.core.insurance.policies.core.mappers.PolicyPartyMapper;
import com.firefly.core.insurance.policies.core.services.PolicyPartyService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyPartyDTO;
import com.firefly.core.insurance.policies.models.entities.PolicyParty;
import com.firefly.core.insurance.policies.models.repositories.PolicyPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class PolicyPartyServiceImpl implements PolicyPartyService {

    @Autowired
    private PolicyPartyRepository repository;

    @Autowired
    private PolicyPartyMapper mapper;

    @Override
    public Mono<PaginationResponse<PolicyPartyDTO>> filterPolicyParties(UUID policyId, FilterRequest<PolicyPartyDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new PolicyPartyDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(PolicyParty.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<PolicyPartyDTO> createPolicyParty(UUID policyId, PolicyPartyDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PolicyPartyDTO> updatePolicyParty(UUID policyId, UUID policyPartyId, PolicyPartyDTO dto) {
        return repository.findById(policyPartyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyParty", policyPartyId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyParty with id '" + policyPartyId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePolicyParty(UUID policyId, UUID policyPartyId) {
        return repository.findById(policyPartyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyParty", policyPartyId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyParty with id '" + policyPartyId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(policyPartyId);
                });
    }

    @Override
    public Mono<PolicyPartyDTO> getPolicyPartyById(UUID policyId, UUID policyPartyId) {
        return repository.findById(policyPartyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PolicyParty", policyPartyId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PolicyParty with id '" + policyPartyId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
