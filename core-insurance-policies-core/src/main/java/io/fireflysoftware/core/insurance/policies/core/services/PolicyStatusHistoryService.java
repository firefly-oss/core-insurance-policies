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
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.PolicyStatusHistoryDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for status change recorded against a policy.
 */
public interface PolicyStatusHistoryService {

    /**
     * Filters policyStatusHistories with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of PolicyStatusHistoryDTO
     */
    Mono<PaginationResponse<PolicyStatusHistoryDTO>> filterPolicyStatusHistories(UUID policyId, FilterRequest<PolicyStatusHistoryDTO> filterRequest);

    /**
     * Creates a PolicyStatusHistory.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<PolicyStatusHistoryDTO> createPolicyStatusHistory(UUID policyId, PolicyStatusHistoryDTO dto);

    /**
     * Updates a PolicyStatusHistory.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<PolicyStatusHistoryDTO> updatePolicyStatusHistory(UUID policyId, UUID policyStatusHistoryId, PolicyStatusHistoryDTO dto);

    /**
     * Deletes a PolicyStatusHistory.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deletePolicyStatusHistory(UUID policyId, UUID policyStatusHistoryId);

    /**
     * Loads a PolicyStatusHistory by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<PolicyStatusHistoryDTO> getPolicyStatusHistoryById(UUID policyId, UUID policyStatusHistoryId);
}
