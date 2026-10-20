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


package io.fireflysoftware.core.insurance.policies.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.HealthDetailDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for member cover on a health policy.
 */
public interface HealthDetailService {

    /**
     * Filters healthDetails with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of HealthDetailDTO
     */
    Mono<PaginationResponse<HealthDetailDTO>> filterHealthDetails(UUID policyId, FilterRequest<HealthDetailDTO> filterRequest);

    /**
     * Creates a HealthDetail.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<HealthDetailDTO> createHealthDetail(UUID policyId, HealthDetailDTO dto);

    /**
     * Updates a HealthDetail.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<HealthDetailDTO> updateHealthDetail(UUID policyId, UUID healthDetailId, HealthDetailDTO dto);

    /**
     * Deletes a HealthDetail.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteHealthDetail(UUID policyId, UUID healthDetailId);

    /**
     * Loads a HealthDetail by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<HealthDetailDTO> getHealthDetailById(UUID policyId, UUID healthDetailId);
}
