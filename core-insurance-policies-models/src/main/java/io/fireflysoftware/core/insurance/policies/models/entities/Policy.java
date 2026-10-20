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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.LineOfBusiness;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Insurance policy master record.
 * Maps to the 'policy' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("policy")
public class Policy {

    @Id
    @Column("policy_id")
    private UUID policyId;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("contract_id")
    private UUID contractId;

    @Column("product_id")
    private UUID productId;

    @Column("policy_number")
    private String policyNumber;

    @Column("line_of_business")
    private LineOfBusiness lineOfBusiness;

    @Column("policy_status")
    private PolicyStatus policyStatus;

    @Column("currency")
    private String currency;

    @Column("inception_date")
    private LocalDate inceptionDate;

    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("renewal_date")
    private LocalDate renewalDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
