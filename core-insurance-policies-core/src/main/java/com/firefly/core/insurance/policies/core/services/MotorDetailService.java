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
import com.firefly.core.insurance.policies.interfaces.dtos.MotorDetailDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for vehicle and principal driver for a motor policy.
 */
public interface MotorDetailService {

    /**
     * Filters motorDetails with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of MotorDetailDTO
     */
    Mono<PaginationResponse<MotorDetailDTO>> filterMotorDetails(UUID policyId, FilterRequest<MotorDetailDTO> filterRequest);

    /**
     * Creates a MotorDetail.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<MotorDetailDTO> createMotorDetail(UUID policyId, MotorDetailDTO dto);

    /**
     * Updates a MotorDetail.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<MotorDetailDTO> updateMotorDetail(UUID policyId, UUID motorDetailId, MotorDetailDTO dto);

    /**
     * Deletes a MotorDetail.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteMotorDetail(UUID policyId, UUID motorDetailId);

    /**
     * Loads a MotorDetail by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<MotorDetailDTO> getMotorDetailById(UUID policyId, UUID motorDetailId);
}
