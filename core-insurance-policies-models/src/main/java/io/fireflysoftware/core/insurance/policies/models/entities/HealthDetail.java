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


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Member cover on a health policy.
 * Maps to the 'health_detail' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("health_detail")
public class HealthDetail {

    @Id
    @Column("health_detail_id")
    private UUID healthDetailId;

    @Column("policy_id")
    private UUID policyId;

    @Column("insured_object_id")
    private UUID insuredObjectId;

    @Column("member_party_id")
    private UUID memberPartyId;

    @Column("plan_code")
    private String planCode;

    @Column("cover_start")
    private LocalDate coverStart;

    @Column("cover_end")
    private LocalDate coverEnd;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
