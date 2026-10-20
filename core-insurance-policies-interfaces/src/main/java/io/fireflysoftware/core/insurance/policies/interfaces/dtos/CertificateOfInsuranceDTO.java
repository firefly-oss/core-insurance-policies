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

import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.CertificateStatus;

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
 * Certificate issued from a policy transaction, reissued when the cover changes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateOfInsuranceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID certificateOfInsuranceId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @NotBlank(message = "certificateNumber is required")
    @Size(max = 64, message = "certificateNumber must not exceed 64 characters")
    private String certificateNumber;

    @FilterableId
    @NotNull(message = "policyTransactionId is required")
    private UUID policyTransactionId;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    @NotNull(message = "holderPartyId is required")
    private UUID holderPartyId;

    @NotNull(message = "certificateStatus is required")
    private CertificateStatus certificateStatus;

    @NotNull(message = "issuedAt is required")
    private LocalDateTime issuedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
