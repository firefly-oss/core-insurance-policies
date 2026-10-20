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


package io.fireflysoftware.core.insurance.policies.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.InsuredObjectType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Risk object attached to a policy transaction.
 * Maps to the 'insured_object' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("insured_object")
public class InsuredObject {

    @Id
    @Column("insured_object_id")
    private UUID insuredObjectId;

    @Column("policy_id")
    private UUID policyId;

    @Column("policy_transaction_id")
    private UUID policyTransactionId;

    @Column("object_type")
    private InsuredObjectType objectType;

    @Column("party_id")
    private UUID partyId;

    @Column("description")
    private String description;

    @Column("identifier")
    private String identifier;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
