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


package com.firefly.core.insurance.policies.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Coverage terms recorded against a policy transaction.
 * Maps to the 'coverage' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("coverage")
public class Coverage {

    @Id
    @Column("coverage_id")
    private UUID coverageId;

    @Column("policy_id")
    private UUID policyId;

    @Column("policy_transaction_id")
    private UUID policyTransactionId;

    @Column("coverage_code")
    private String coverageCode;

    @Column("coverage_name")
    private String coverageName;

    @Column("limit_amount")
    private BigDecimal limitAmount;

    @Column("deductible_amount")
    private BigDecimal deductibleAmount;

    @Column("currency")
    private String currency;

    @Column("premium_amount")
    private BigDecimal premiumAmount;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
