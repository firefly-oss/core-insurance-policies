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


package com.firefly.core.insurance.policies.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.policies.interfaces.dtos.CertificateOfInsuranceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for certificate issued from a policy transaction, reissued when the cover changes.
 */
public interface CertificateOfInsuranceService {

    /**
     * Filters certificatesOfInsurance with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of CertificateOfInsuranceDTO
     */
    Mono<PaginationResponse<CertificateOfInsuranceDTO>> filterCertificatesOfInsurance(UUID policyId, FilterRequest<CertificateOfInsuranceDTO> filterRequest);

    /**
     * Creates a CertificateOfInsurance.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<CertificateOfInsuranceDTO> createCertificateOfInsurance(UUID policyId, CertificateOfInsuranceDTO dto);

    /**
     * Updates a CertificateOfInsurance.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<CertificateOfInsuranceDTO> updateCertificateOfInsurance(UUID policyId, UUID certificateOfInsuranceId, CertificateOfInsuranceDTO dto);

    /**
     * Deletes a CertificateOfInsurance.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteCertificateOfInsurance(UUID policyId, UUID certificateOfInsuranceId);

    /**
     * Loads a CertificateOfInsurance by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<CertificateOfInsuranceDTO> getCertificateOfInsuranceById(UUID policyId, UUID certificateOfInsuranceId);
}
