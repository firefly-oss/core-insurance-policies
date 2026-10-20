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
import com.firefly.core.insurance.policies.core.mappers.PropertyDetailMapper;
import com.firefly.core.insurance.policies.core.services.PropertyDetailService;
import com.firefly.core.insurance.policies.interfaces.dtos.PropertyDetailDTO;
import com.firefly.core.insurance.policies.models.entities.PropertyDetail;
import com.firefly.core.insurance.policies.models.repositories.PropertyDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class PropertyDetailServiceImpl implements PropertyDetailService {

    @Autowired
    private PropertyDetailRepository repository;

    @Autowired
    private PropertyDetailMapper mapper;

    @Override
    public Mono<PaginationResponse<PropertyDetailDTO>> filterPropertyDetails(UUID policyId, FilterRequest<PropertyDetailDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new PropertyDetailDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(PropertyDetail.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<PropertyDetailDTO> createPropertyDetail(UUID policyId, PropertyDetailDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PropertyDetailDTO> updatePropertyDetail(UUID policyId, UUID propertyDetailId, PropertyDetailDTO dto) {
        return repository.findById(propertyDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PropertyDetail", propertyDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PropertyDetail with id '" + propertyDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePropertyDetail(UUID policyId, UUID propertyDetailId) {
        return repository.findById(propertyDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PropertyDetail", propertyDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PropertyDetail with id '" + propertyDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(propertyDetailId);
                });
    }

    @Override
    public Mono<PropertyDetailDTO> getPropertyDetailById(UUID policyId, UUID propertyDetailId) {
        return repository.findById(propertyDetailId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("PropertyDetail", propertyDetailId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "PropertyDetail with id '" + propertyDetailId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
