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
 * Life assured and sum assured on a life policy.
 * Maps to the 'life_detail' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("life_detail")
public class LifeDetail {

    @Id
    @Column("life_detail_id")
    private UUID lifeDetailId;

    @Column("policy_id")
    private UUID policyId;

    @Column("insured_object_id")
    private UUID insuredObjectId;

    @Column("life_assured_party_id")
    private UUID lifeAssuredPartyId;

    @Column("sum_assured")
    private BigDecimal sumAssured;

    @Column("term_years")
    private Integer termYears;

    @Column("currency")
    private String currency;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
