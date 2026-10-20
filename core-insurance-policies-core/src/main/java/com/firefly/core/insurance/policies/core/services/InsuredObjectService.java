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
import com.firefly.core.insurance.policies.interfaces.dtos.InsuredObjectDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for risk object attached to a policy transaction.
 */
public interface InsuredObjectService {

    /**
     * Filters insuredObjects with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of InsuredObjectDTO
     */
    Mono<PaginationResponse<InsuredObjectDTO>> filterInsuredObjects(UUID policyId, FilterRequest<InsuredObjectDTO> filterRequest);

    /**
     * Creates a InsuredObject.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<InsuredObjectDTO> createInsuredObject(UUID policyId, InsuredObjectDTO dto);

    /**
     * Updates a InsuredObject.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<InsuredObjectDTO> updateInsuredObject(UUID policyId, UUID insuredObjectId, InsuredObjectDTO dto);

    /**
     * Deletes a InsuredObject.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteInsuredObject(UUID policyId, UUID insuredObjectId);

    /**
     * Loads a InsuredObject by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<InsuredObjectDTO> getInsuredObjectById(UUID policyId, UUID insuredObjectId);
}
