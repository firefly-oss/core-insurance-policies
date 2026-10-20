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
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.PropertyDetailDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for location and sums for a property policy, including home and commercial premises.
 */
public interface PropertyDetailService {

    /**
     * Filters propertyDetails with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of PropertyDetailDTO
     */
    Mono<PaginationResponse<PropertyDetailDTO>> filterPropertyDetails(UUID policyId, FilterRequest<PropertyDetailDTO> filterRequest);

    /**
     * Creates a PropertyDetail.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<PropertyDetailDTO> createPropertyDetail(UUID policyId, PropertyDetailDTO dto);

    /**
     * Updates a PropertyDetail.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<PropertyDetailDTO> updatePropertyDetail(UUID policyId, UUID propertyDetailId, PropertyDetailDTO dto);

    /**
     * Deletes a PropertyDetail.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deletePropertyDetail(UUID policyId, UUID propertyDetailId);

    /**
     * Loads a PropertyDetail by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<PropertyDetailDTO> getPropertyDetailById(UUID policyId, UUID propertyDetailId);
}
