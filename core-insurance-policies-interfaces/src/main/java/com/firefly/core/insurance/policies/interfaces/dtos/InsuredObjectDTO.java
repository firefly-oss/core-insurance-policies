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


package com.firefly.core.insurance.policies.interfaces.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.policies.interfaces.enums.InsuredObjectType;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Risk object attached to a policy transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuredObjectDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID insuredObjectId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "policyTransactionId is required")
    private UUID policyTransactionId;

    @NotNull(message = "objectType is required")
    private InsuredObjectType objectType;

    /** Logical reference: core-common-customer-mgmt party, when the object is a person. This service does not own that record. */
    @FilterableId
    private UUID partyId;

    @Size(max = 4000, message = "description must not exceed 4000 characters")
    private String description;

    @Size(max = 128, message = "identifier must not exceed 128 characters")
    private String identifier;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
