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


package io.fireflysoftware.core.insurance.policies.interfaces.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.LineOfBusiness;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Insurance policy master record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID policyId;

    /** Logical reference: Firefly deployment tenant. This service does not own that record. */
    @FilterableId
    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    /** Logical reference: core-common-contract-mgmt contract. This service does not own that record. */
    @FilterableId
    @NotNull(message = "contractId is required")
    private UUID contractId;

    /** Logical reference: core-common-product-mgmt product. This service does not own that record. */
    @FilterableId
    @NotNull(message = "productId is required")
    private UUID productId;

    @NotBlank(message = "policyNumber is required")
    @Size(max = 64, message = "policyNumber must not exceed 64 characters")
    private String policyNumber;

    @NotNull(message = "lineOfBusiness is required")
    private LineOfBusiness lineOfBusiness;

    @NotNull(message = "policyStatus is required")
    private PolicyStatus policyStatus;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    @NotNull(message = "inceptionDate is required")
    private LocalDate inceptionDate;

    private LocalDate expiryDate;

    private LocalDate renewalDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
