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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.policies.interfaces.enums.PolicyTransactionStatus;
import com.firefly.core.insurance.policies.interfaces.enums.PolicyTransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * New business, endorsement, renewal, cancellation, or reinstatement on a policy.
 * Maps to the 'policy_transaction' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("policy_transaction")
public class PolicyTransaction {

    @Id
    @Column("policy_transaction_id")
    private UUID policyTransactionId;

    @Column("policy_id")
    private UUID policyId;

    @Column("transaction_number")
    private String transactionNumber;

    @Column("transaction_type")
    private PolicyTransactionType transactionType;

    @Column("transaction_status")
    private PolicyTransactionStatus transactionStatus;

    @Column("effective_date")
    private LocalDate effectiveDate;

    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("sequence_number")
    private Integer sequenceNumber;

    @Column("out_of_sequence")
    private Boolean outOfSequence;

    @Column("notice_days")
    private Integer noticeDays;

    @Column("reason")
    private String reason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
