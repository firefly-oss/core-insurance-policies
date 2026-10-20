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

import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyPartyRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Party role on a policy. The party itself is owned by customer management..
 * Maps to the 'policy_party' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("policy_party")
public class PolicyParty {

    @Id
    @Column("policy_party_id")
    private UUID policyPartyId;

    @Column("policy_id")
    private UUID policyId;

    @Column("party_id")
    private UUID partyId;

    @Column("role")
    private PolicyPartyRole role;

    @Column("effective_from")
    private LocalDate effectiveFrom;

    @Column("effective_to")
    private LocalDate effectiveTo;

    @Column("active")
    private Boolean active;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
