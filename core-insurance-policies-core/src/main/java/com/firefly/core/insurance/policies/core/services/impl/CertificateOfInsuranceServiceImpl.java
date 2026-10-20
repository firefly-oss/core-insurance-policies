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
import com.firefly.core.insurance.policies.core.mappers.CertificateOfInsuranceMapper;
import com.firefly.core.insurance.policies.core.services.CertificateOfInsuranceService;
import com.firefly.core.insurance.policies.interfaces.dtos.CertificateOfInsuranceDTO;
import com.firefly.core.insurance.policies.models.entities.CertificateOfInsurance;
import com.firefly.core.insurance.policies.models.repositories.CertificateOfInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class CertificateOfInsuranceServiceImpl implements CertificateOfInsuranceService {

    @Autowired
    private CertificateOfInsuranceRepository repository;

    @Autowired
    private CertificateOfInsuranceMapper mapper;

    @Override
    public Mono<PaginationResponse<CertificateOfInsuranceDTO>> filterCertificatesOfInsurance(UUID policyId, FilterRequest<CertificateOfInsuranceDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new CertificateOfInsuranceDTO());
        }
        filterRequest.getFilters().setPolicyId(policyId);
        return FilterUtils.createFilter(CertificateOfInsurance.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<CertificateOfInsuranceDTO> createCertificateOfInsurance(UUID policyId, CertificateOfInsuranceDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setPolicyId(policyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CertificateOfInsuranceDTO> updateCertificateOfInsurance(UUID policyId, UUID certificateOfInsuranceId, CertificateOfInsuranceDTO dto) {
        return repository.findById(certificateOfInsuranceId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CertificateOfInsurance", certificateOfInsuranceId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CertificateOfInsurance with id '" + certificateOfInsuranceId + "' does not belong to policy '" + policyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCertificateOfInsurance(UUID policyId, UUID certificateOfInsuranceId) {
        return repository.findById(certificateOfInsuranceId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CertificateOfInsurance", certificateOfInsuranceId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CertificateOfInsurance with id '" + certificateOfInsuranceId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return repository.deleteById(certificateOfInsuranceId);
                });
    }

    @Override
    public Mono<CertificateOfInsuranceDTO> getCertificateOfInsuranceById(UUID policyId, UUID certificateOfInsuranceId) {
        return repository.findById(certificateOfInsuranceId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CertificateOfInsurance", certificateOfInsuranceId.toString())))
                .flatMap(existing -> {
                    if (!policyId.equals(existing.getPolicyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CertificateOfInsurance with id '" + certificateOfInsuranceId + "' does not belong to policy '" + policyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
