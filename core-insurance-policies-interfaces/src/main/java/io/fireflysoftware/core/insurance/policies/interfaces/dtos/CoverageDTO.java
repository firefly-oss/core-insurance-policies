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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


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
 * Coverage terms recorded against a policy transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID coverageId;

    @FilterableId
    @NotNull(message = "Policy id is required")
    private UUID policyId;

    @FilterableId
    @NotNull(message = "policyTransactionId is required")
    private UUID policyTransactionId;

    @NotBlank(message = "coverageCode is required")
    @Size(max = 64, message = "coverageCode must not exceed 64 characters")
    private String coverageCode;

    @Size(max = 255, message = "coverageName must not exceed 255 characters")
    private String coverageName;

    private BigDecimal limitAmount;

    private BigDecimal deductibleAmount;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    private BigDecimal premiumAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
