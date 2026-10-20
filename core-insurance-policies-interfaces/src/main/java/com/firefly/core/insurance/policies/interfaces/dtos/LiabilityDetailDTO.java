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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.policies.interfaces.enums.ExposureBasis;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Exposure for a liability policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiabilityDetailDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID liabilityDetailId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "insuredObjectId is required")
    private UUID insuredObjectId;

    @NotNull(message = "exposureBasis is required")
    private ExposureBasis exposureBasis;

    private BigDecimal exposureAmount;

    @Size(max = 128, message = "territory must not exceed 128 characters")
    private String territory;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
