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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Member cover on a health policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthDetailDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID healthDetailId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "insuredObjectId is required")
    private UUID insuredObjectId;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    @NotNull(message = "memberPartyId is required")
    private UUID memberPartyId;

    @NotBlank(message = "planCode is required")
    @Size(max = 64, message = "planCode must not exceed 64 characters")
    private String planCode;

    @NotNull(message = "coverStart is required")
    private LocalDate coverStart;

    private LocalDate coverEnd;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
