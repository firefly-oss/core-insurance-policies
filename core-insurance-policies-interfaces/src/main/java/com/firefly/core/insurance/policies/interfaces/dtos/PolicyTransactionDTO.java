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

import com.firefly.core.insurance.policies.interfaces.enums.PolicyTransactionStatus;
import com.firefly.core.insurance.policies.interfaces.enums.PolicyTransactionType;

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
 * New business, endorsement, renewal, cancellation, or reinstatement on a policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyTransactionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID policyTransactionId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @NotBlank(message = "transactionNumber is required")
    @Size(max = 64, message = "transactionNumber must not exceed 64 characters")
    private String transactionNumber;

    @NotNull(message = "transactionType is required")
    private PolicyTransactionType transactionType;

    @NotNull(message = "transactionStatus is required")
    private PolicyTransactionStatus transactionStatus;

    @NotNull(message = "effectiveDate is required")
    private LocalDate effectiveDate;

    private LocalDate expiryDate;

    @NotNull(message = "sequenceNumber is required")
    private Integer sequenceNumber;

    private Boolean outOfSequence;

    private Integer noticeDays;

    @Size(max = 4000, message = "reason must not exceed 4000 characters")
    private String reason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
