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
 * Location and sums for a property policy, including home and commercial premises.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDetailDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID propertyDetailId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "insuredObjectId is required")
    private UUID insuredObjectId;

    @Size(max = 128, message = "occupancy must not exceed 128 characters")
    private String occupancy;

    @Size(max = 128, message = "construction must not exceed 128 characters")
    private String construction;

    private Integer yearBuilt;

    @Size(max = 255, message = "addressLine must not exceed 255 characters")
    private String addressLine;

    @Size(max = 100, message = "city must not exceed 100 characters")
    private String city;

    @Size(max = 20, message = "postalCode must not exceed 20 characters")
    private String postalCode;

    @Size(max = 2, message = "countryCode must not exceed 2 characters")
    private String countryCode;

    private BigDecimal buildingsSum;

    private BigDecimal contentsSum;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
