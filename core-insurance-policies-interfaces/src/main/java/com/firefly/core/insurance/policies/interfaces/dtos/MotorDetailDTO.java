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

import com.firefly.core.insurance.policies.interfaces.enums.VehicleUse;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Vehicle and principal driver for a motor policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorDetailDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID motorDetailId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "insuredObjectId is required")
    private UUID insuredObjectId;

    @Size(max = 64, message = "vehicleIdentifier must not exceed 64 characters")
    private String vehicleIdentifier;

    @Size(max = 64, message = "make must not exceed 64 characters")
    private String make;

    @Size(max = 64, message = "model must not exceed 64 characters")
    private String model;

    private Integer modelYear;

    @NotNull(message = "vehicleUse is required")
    private VehicleUse vehicleUse;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    private UUID driverPartyId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
