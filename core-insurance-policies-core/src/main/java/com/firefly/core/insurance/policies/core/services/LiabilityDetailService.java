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
import com.firefly.core.insurance.policies.interfaces.dtos.LiabilityDetailDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for exposure for a liability policy.
 */
public interface LiabilityDetailService {

    /**
     * Filters liabilityDetails with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of LiabilityDetailDTO
     */
    Mono<PaginationResponse<LiabilityDetailDTO>> filterLiabilityDetails(UUID policyId, FilterRequest<LiabilityDetailDTO> filterRequest);

    /**
     * Creates a LiabilityDetail.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<LiabilityDetailDTO> createLiabilityDetail(UUID policyId, LiabilityDetailDTO dto);

    /**
     * Updates a LiabilityDetail.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<LiabilityDetailDTO> updateLiabilityDetail(UUID policyId, UUID liabilityDetailId, LiabilityDetailDTO dto);

    /**
     * Deletes a LiabilityDetail.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteLiabilityDetail(UUID policyId, UUID liabilityDetailId);

    /**
     * Loads a LiabilityDetail by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<LiabilityDetailDTO> getLiabilityDetailById(UUID policyId, UUID liabilityDetailId);
}
