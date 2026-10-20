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

import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.policies.interfaces.enums.CertificateStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Certificate issued from a policy transaction, reissued when the cover changes.
 * Maps to the 'certificate_of_insurance' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("certificate_of_insurance")
public class CertificateOfInsurance {

    @Id
    @Column("certificate_of_insurance_id")
    private UUID certificateOfInsuranceId;

    @Column("policy_id")
    private UUID policyId;

    @Column("certificate_number")
    private String certificateNumber;

    @Column("policy_transaction_id")
    private UUID policyTransactionId;

    @Column("holder_party_id")
    private UUID holderPartyId;

    @Column("certificate_status")
    private CertificateStatus certificateStatus;

    @Column("issued_at")
    private LocalDateTime issuedAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
